package pl.edu.pg.eti.kask.rpg.building.controller.api;

import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitsResponse;

import java.util.UUID;

public interface OrganizationalUnitController {

    GetOrganizationalUnitsResponse getOrganizationalUnits();

    GetOrganizationalUnitResponse getOrganizationalUnit(UUID uuid);

    void deleteOrganizationalUnit(UUID uuid);
}
