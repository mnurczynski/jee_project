package pl.edu.pg.eti.kask.rpg.building.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import pl.edu.pg.eti.kask.rpg.building.controller.api.BuildingController;
import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingsResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.PatchBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.dto.PutBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.service.BuildingService;
import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.user.entity.Type;

import java.util.Optional;
import java.util.UUID;


@Path("")
@RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
public class BuildingRestController implements BuildingController {


    private final BuildingService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    private final SecurityContext securityContext;

    /**
     * @param service building service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public BuildingRestController(BuildingService service, DtoFunctionFactory factory, SecurityContext securityContext) {
        this.service = service;
        this.factory = factory;
        this.securityContext = securityContext;
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public GetBuildingsResponse getBuildings()
    {
        return service.findAllForCallerPrincipal().map(factory.buildingsToResponse()).orElseThrow(NotFoundException::new);
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public GetBuildingsResponse getOrganizationalUnitBuildings(UUID id)
    {
        return service.findAllByOrganizationalUnit(id).map(factory.buildingsToResponse()).orElseThrow(NotFoundException::new);
    }

    @RolesAllowed( {Type.MANAGER, Type.BUILDING_ADMINISTRATOR})
    public GetBuildingsResponse getUserBuildings(UUID id)
    {
        return service.findAllByUser(id).map(factory.buildingsToResponse()).orElseThrow(NotFoundException::new);
    }


    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public GetBuildingResponse getBuilding(UUID id, UUID org_uuid)
    {
        var building = service.findForCallerPrincipal(id);
        if(building.isPresent() && building.get().getOccupant().getId().equals(org_uuid)) {
            return factory.buildingToResponse().apply(building.get());
        }
        throw new NotFoundException();
    }

    @RolesAllowed({Type.MANAGER, Type.BUILDING_ADMINISTRATOR})
    public void putBuilding(UUID id, PutBuildingRequest request, UUID org_uuid)
    {
        try
        {
            deleteBuilding(id, org_uuid); // Deleting building if already exists
        }
        catch(Exception ignored)
        {

        }
        if(!request.getOrganizationalUnit().equals(org_uuid))
        {
            throw new BadRequestException("Organizational unit does not match");
        }
        service.createForCallerPrincipal(factory.requestToBuilding().apply(id, request));
    }

    @RolesAllowed({Type.MANAGER, Type.BUILDING_ADMINISTRATOR})
    public void patchBuilding(UUID id, PatchBuildingRequest request, UUID org_uuid)
    {
        Optional<Building> building = service.find(id);
        if(building.isPresent())
        {
            service.update(factory.updateBuilding().apply(building.get(), request));
            return;
        }
        throw new NotFoundException();
    }

    @RolesAllowed({Type.MANAGER, Type.BUILDING_ADMINISTRATOR})
    public void deleteBuilding(UUID id, UUID org_uuid)
    {
        var building = service.find(id);
        if(building.isPresent() && building.get().getOccupant().getId().equals(org_uuid))
        {
            service.delete(id);
        }
        else
        {
            throw new NotFoundException();
        }
    }

}
