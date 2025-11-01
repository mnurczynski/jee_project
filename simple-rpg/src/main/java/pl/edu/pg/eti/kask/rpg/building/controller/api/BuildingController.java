package pl.edu.pg.eti.kask.rpg.building.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingsResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.PatchBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.dto.PutBuildingRequest;

import java.util.UUID;

@Path("")
public interface BuildingController {

    @GET
    @Path("/buildings")
    @Produces(MediaType.APPLICATION_JSON)
    GetBuildingsResponse getBuildings();

    @GET
    @Path("/organizational_units/{id}/buildings")
    @Produces(MediaType.APPLICATION_JSON)
    GetBuildingsResponse getOrganizationalUnitBuildings(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/buildings")
    @Produces(MediaType.APPLICATION_JSON)
    GetBuildingsResponse getUserBuildings(@PathParam("id") UUID id);

    @GET
    @Path("/buildings/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetBuildingResponse getBuilding(@PathParam("id") UUID uuid);

    @PUT
    @Path("/buildings/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putBuilding(@PathParam("id") UUID id, PutBuildingRequest request);

    @PATCH
    @Path("/buildings/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void patchBuilding(@PathParam("id") UUID id, PatchBuildingRequest request);

    @DELETE
    @Path("/buildings/{id}")
    void deleteBuilding(@PathParam("id") UUID id);

}
