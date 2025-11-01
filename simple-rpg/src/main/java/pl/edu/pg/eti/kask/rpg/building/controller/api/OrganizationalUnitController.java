package pl.edu.pg.eti.kask.rpg.building.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitsResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.PatchOrganizationalUnitRequest;
import pl.edu.pg.eti.kask.rpg.building.dto.PutOrganizationalUnitRequest;

import java.util.UUID;

@Path("")
public interface OrganizationalUnitController {

    @GET
    @Path("/organizational_units")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrganizationalUnitsResponse getOrganizationalUnits();

    @GET
    @Path("/organizational_units/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrganizationalUnitResponse getOrganizationalUnit(@PathParam("id") UUID uuid);

    @DELETE
    @Path("/organizational_units/{id}")
    void deleteOrganizationalUnit(@PathParam("id") UUID uuid);

    @PUT
    @Path("/organizational_units/{id}")
    void putOrganizationalUnit(@PathParam("id") UUID uuid, PutOrganizationalUnitRequest organizationalUnit);

    @PATCH
    @Path("/organizational_units/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchOrganizationalUnit(@PathParam("id") UUID uuid, PatchOrganizationalUnitRequest organizationalUnit);
}
