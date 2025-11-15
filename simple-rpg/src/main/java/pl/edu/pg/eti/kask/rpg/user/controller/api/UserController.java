package pl.edu.pg.eti.kask.rpg.user.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.PutUserRequest;

import java.awt.image.BufferedImage;
import java.util.UUID;

@Path("")
public interface UserController {

    @GET
    @Path("/users/{id}/avatar")
    @Produces("multipart/form-data")
    public BufferedImage getUserAvatar(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}/avatar")
    @Consumes("multipart/form-data")
    public void putUserAvatar(@PathParam("id") UUID id, BufferedImage image);

    @DELETE
    @Path("/users/{id}/avatar")
    public void deleteUserAvatar(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GetUserResponse getUser(@PathParam("id") UUID id);

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public GetUsersResponse getUsers();

    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putUser(@PathParam("id") UUID id, PutUserRequest request);
}
