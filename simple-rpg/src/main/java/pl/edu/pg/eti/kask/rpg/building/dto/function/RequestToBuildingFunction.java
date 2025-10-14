package pl.edu.pg.eti.kask.rpg.building.dto.function;

import pl.edu.pg.eti.kask.rpg.building.dto.PutBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToBuildingFunction implements BiFunction<UUID, PutBuildingRequest, Building> {
    public Building apply(UUID id, PutBuildingRequest request) {
        return Building.builder()
                .id(id)
                .name(request.getName())
                .number(request.getNumber())
                .area(request.getArea())
                .maximumOccupancy(request.getMaximumOccupancy())

                .occupant(OrganizationalUnit.builder()
                        .id(request.getOrganizationalUnit())
                        .build())
                .buildingAdministrator(User.builder().id(request.getBuildingAdministrator()).build())
                .build();
    }

}
