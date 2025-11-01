package pl.edu.pg.eti.kask.rpg.building.dto.function;

import pl.edu.pg.eti.kask.rpg.building.dto.PatchOrganizationalUnitRequest;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;

import java.util.function.BiFunction;

public class UpdateOrganizationalUnitWithRequestFunction implements BiFunction<OrganizationalUnit, PatchOrganizationalUnitRequest, OrganizationalUnit> {
    public OrganizationalUnit apply(OrganizationalUnit entity, PatchOrganizationalUnitRequest request) {
        return OrganizationalUnit.builder()
                .id(entity.getId())
                .name(entity.getName())
                .required_occupancy(request.getRequired_occupancy())
                .type(entity.getType())
                .buildingsOccupied(entity.getBuildingsOccupied())
                .build();
    }

}