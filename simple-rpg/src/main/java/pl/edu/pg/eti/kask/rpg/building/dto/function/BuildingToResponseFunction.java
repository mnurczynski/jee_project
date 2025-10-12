package pl.edu.pg.eti.kask.rpg.building.dto.function;

import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingResponse;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;

import java.util.function.Function;

public class BuildingToResponseFunction implements Function<Building, GetBuildingResponse> {

    public GetBuildingResponse apply(Building entity) {
        return GetBuildingResponse.builder()
                .organizationalUnit(GetBuildingResponse.OrganizationalUnit.builder()
                        .id(entity.getOccupant().getId())
                        .name(entity.getOccupant().getName())
                        .build())
                .number(entity.getNumber())
                .name(entity.getName())
                .area(entity.getArea())
                .maximumOccupancy(entity.getMaximumOccupancy())
                .id(entity.getId())
                .build();
    }

}
