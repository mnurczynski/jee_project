package pl.edu.pg.eti.kask.rpg.building.controller.api;

import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingsResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.PatchBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.dto.PutBuildingRequest;

import java.util.UUID;

public interface BuildingController {
    GetBuildingsResponse getBuildings();


    GetBuildingsResponse getOrganizationalUnitBuildings(UUID id);


    GetBuildingsResponse getUserBuildings(UUID id);


    GetBuildingResponse getBuilding(UUID uuid);

    void putBuilding(UUID id, PutBuildingRequest request);


    void patchBuilding(UUID id, PatchBuildingRequest request);

    void deleteBuilding(UUID id);

}
