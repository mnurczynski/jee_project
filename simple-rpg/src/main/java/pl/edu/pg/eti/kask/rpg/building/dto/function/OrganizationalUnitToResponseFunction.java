package pl.edu.pg.eti.kask.rpg.building.dto.function;

import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitResponse;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;

import java.util.function.Function;

public class OrganizationalUnitToResponseFunction implements Function<OrganizationalUnit, GetOrganizationalUnitResponse> {
    public GetOrganizationalUnitResponse apply(OrganizationalUnit entity) {
        return GetOrganizationalUnitResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .required_occupancy(entity.getRequired_occupancy())
                .type(entity.getType())
                .build();
    }

}
