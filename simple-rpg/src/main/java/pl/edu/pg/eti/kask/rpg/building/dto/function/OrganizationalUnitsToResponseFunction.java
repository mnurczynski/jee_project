package pl.edu.pg.eti.kask.rpg.building.dto.function;

import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitsResponse;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;

import java.util.List;
import java.util.function.Function;

public class OrganizationalUnitsToResponseFunction implements Function<List<OrganizationalUnit>, GetOrganizationalUnitsResponse> {
    public GetOrganizationalUnitsResponse apply(List<OrganizationalUnit> entities) {
        return GetOrganizationalUnitsResponse.builder()
                .organizationalUnitList(entities.stream()
                        .map(unit -> GetOrganizationalUnitsResponse.OrganizationalUnit.builder()
                                .id(unit.getId())
                                .name(unit.getName())
                                .build())
                        .toList())
                .build();
    }

}
