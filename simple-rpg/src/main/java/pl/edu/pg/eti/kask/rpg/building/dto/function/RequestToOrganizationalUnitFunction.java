package pl.edu.pg.eti.kask.rpg.building.dto.function;

import pl.edu.pg.eti.kask.rpg.building.dto.PutOrganizationalUnitRequest;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToOrganizationalUnitFunction implements BiFunction<UUID, PutOrganizationalUnitRequest, OrganizationalUnit> {
    public OrganizationalUnit apply(UUID id, PutOrganizationalUnitRequest request) {
        return OrganizationalUnit.builder()
                .id(id)
                .name(request.getName())
                .required_occupancy(request.getRequired_occupancy())
                .type(request.getType())
                .build();
    }

}
