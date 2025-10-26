package pl.edu.pg.eti.kask.rpg.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.edu.pg.eti.kask.rpg.building.controller.api.BuildingController;
import pl.edu.pg.eti.kask.rpg.building.controller.api.OrganizationalUnitController;
import pl.edu.pg.eti.kask.rpg.building.dto.PatchBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.dto.PutBuildingRequest;
import pl.edu.pg.eti.kask.rpg.user.controller.api.UserController;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Central API servlet for fetching all request from the client and preparing responses. Servlet API does not allow
 * named path parameters so wildcard is used.
 */
@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    /**
     * Controller for managing collections buildings' repre
     * sentations.
     */
    private final BuildingController buildingController;

    /**
     * Controller for managing collections organizationalUnits' representations.
     */
    private final OrganizationalUnitController organizationalUnitController;

    @Inject
    public ApiServlet(BuildingController buildingController, OrganizationalUnitController organizationalUnitController, UserController userController) {
        this.buildingController = buildingController;
        this.organizationalUnitController = organizationalUnitController;
        this.userController = userController;
    }

    private final UserController userController;

    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static final class Paths {

        /**
         * All API operations. Version v1 will be used to distinguish from other implementations.
         */
        public static final String API = "/api";

    }

    /**
     * Patterns used for checking servlet path.
     */
    public static final class Patterns {

        /**
         * UUID
         */
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        /**
         * All buildings.
         */
        public static final Pattern BUILDINGS = Pattern.compile("/buildings/?");

        /**
         * Single building.
         */
        public static final Pattern BUILDING = Pattern.compile("/buildings/(%s)".formatted(UUID.pattern()));

        /**
         * All units.
         */
        public static final Pattern ORGANIZATIONAL_UNITS = Pattern.compile("/organizational_units/?");

        public static final Pattern ORGANIZATIONAL_UNIT = Pattern.compile("/organizational_units/(%s)".formatted(UUID.pattern()));

        /**
         * All buildings assigned to a single unit.
         */
        public static final Pattern ORGANIZATIONAL_UNIT_BUILDINGS = Pattern.compile("/organizational_units/(%s)/buildings/?".formatted(UUID.pattern()));

        /**
         * All buildings of single user.
         */
        public static final Pattern USER_BUILDINGS = Pattern.compile("/users/(%s)/buildings/?".formatted(UUID.pattern()));

        public static final Pattern USER_AVATAR = Pattern.compile("/users/(%s)/avatar/?".formatted(UUID.pattern()));

        public static final Pattern USERS = Pattern.compile("/users/?");
        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));

    }

    /**
     * JSON-B mapping object. According to open liberty documentation creating this is expensive. The JSON-B is only one
     * of many solutions. JSON strings can be built by hand {@link StringBuilder} or with JSON-P API. Both JSON-B and
     * JSON-P are part of Jakarta EE whereas JSON-B is newer standard.
     */
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }



    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.BUILDINGS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(buildingController.getBuildings()));
                return;
            } else if (path.matches(Patterns.BUILDING.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.BUILDING, path);
                response.getWriter().write(jsonb.toJson(buildingController.getBuilding(uuid)));
                return;
            } else if (path.matches(Patterns.ORGANIZATIONAL_UNITS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(organizationalUnitController.getOrganizationalUnits()));
                return;
            } else if (path.matches(Patterns.ORGANIZATIONAL_UNIT_BUILDINGS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.ORGANIZATIONAL_UNIT_BUILDINGS, path);
                response.getWriter().write(jsonb.toJson(buildingController.getOrganizationalUnitBuildings(uuid)));
                return;
            } else if (path.matches(Patterns.USER_BUILDINGS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER_BUILDINGS, path);
                response.getWriter().write(jsonb.toJson(buildingController.getUserBuildings(uuid)));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())){
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                byte [] avatar = userController.getUserAvatar(uuid);
                response.setContentLength(avatar.length);
                response.getOutputStream().write(avatar);
                return;
            }
            else if (path.matches(Patterns.USERS.pattern()))
            {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            }
            else if (path.matches(Patterns.USER.pattern()))
            {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                return;
            }
            else if(path.matches(Patterns.ORGANIZATIONAL_UNIT.pattern()))
            {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.ORGANIZATIONAL_UNIT, path);
                response.getWriter().write(jsonb.toJson(organizationalUnitController.getOrganizationalUnit(uuid)));
                return;
            }

        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.BUILDING.pattern())) {
                UUID uuid = extractUuid(Patterns.BUILDING, path);
                buildingController.putBuilding(uuid, jsonb.fromJson(request.getReader(), PutBuildingRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "buildings", uuid.toString()));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                userController.putUserAvatar(uuid, request.getPart("portrait").getInputStream().readAllBytes());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.BUILDING.pattern())) {
                UUID uuid = extractUuid(Patterns.BUILDING, path);
                buildingController.deleteBuilding(uuid);
                return;
            } else if(path.matches(Patterns.USER_AVATAR.pattern())){
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                userController.deleteUserAvatar(uuid);
                return;
            } else if(path.matches(Patterns.ORGANIZATIONAL_UNIT.pattern())) {
                UUID uuid = extractUuid(Patterns.ORGANIZATIONAL_UNIT, path);
                organizationalUnitController.deleteOrganizationalUnit(uuid);
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a PATCH request.
     *
     * @param request  {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the PATCH cannot be handled
     * @throws IOException      if an input or output error occurs while the servlet is handling the PATCH request
     */
    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.BUILDING.pattern())) {
                UUID uuid = extractUuid(Patterns.BUILDING, path);
                buildingController.patchBuilding(uuid, jsonb.fromJson(request.getReader(), PatchBuildingRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Extracts UUID from path using provided pattern. Pattern needs to contain UUID in first regular expression group.
     *
     * @param pattern regular expression pattern with
     * @param path    request path containing UUID
     * @return extracted UUID
     */
    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    /**
     * Gets path info from the request and returns it. No null is returned, instead empty string is used.
     *
     * @param request original servlet request
     * @return path info (not null)
     */
    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    /**
     * Creates URL using host, port and context root from servlet request and any number of path elements. If any of
     * path elements starts or ends with '/' building, that building is removed.
     *
     * @param request servlet request
     * @param paths   any (can be none) number of path elements
     * @return created url
     */
    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}
