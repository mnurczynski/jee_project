package pl.edu.pg.eti.kask.rpg.building.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.rpg.building.controller.api.BuildingController;
import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingsResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.PatchBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.dto.PutBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.service.BuildingService;
import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.NotFoundException;

import java.util.Optional;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class BuildingSimpleController implements BuildingController {


    private final BuildingService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * @param service character service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public BuildingSimpleController(BuildingService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public GetBuildingsResponse getBuildings()
    {
        return service.findAll().map(factory.buildingsToResponse()).orElseThrow(NotFoundException::new);
    }


    public GetBuildingsResponse getOrganizationalUnitBuildings(UUID id)
    {
        return service.findAllByOrganizationalUnit(id).map(factory.buildingsToResponse()).orElseThrow(NotFoundException::new);
    }


    public GetBuildingsResponse getUserBuildings(UUID id)
    {
        return service.findAllByUser(id).map(factory.buildingsToResponse()).orElseThrow(NotFoundException::new);
    }



    public GetBuildingResponse getBuilding(UUID id)
    {
        return service.find(id).map(factory.buildingToResponse()).orElseThrow(NotFoundException::new);
    }

    public void putBuilding(UUID id, PutBuildingRequest request)
    {
        deleteBuilding(id); // Deleting building if already exists
        service.create(factory.requestToBuilding().apply(id, request));
    }


    public void patchBuilding(UUID id, PatchBuildingRequest request)
    {
        Optional<Building> building = service.find(id);
        if(building.isPresent())
        {
            service.update(factory.updateBuilding().apply(building.get(), request));
            return;
        }
        throw new NotFoundException();
    }

    public void deleteBuilding(UUID id)
    {
        Optional<Building> building = service.find(id);
        building.ifPresent(service::delete);
    }

}
