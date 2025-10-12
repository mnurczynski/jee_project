package pl.edu.pg.eti.kask.rpg.building.dto.function;

import pl.edu.pg.eti.kask.rpg.building.dto.PatchBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;

import java.util.function.BiFunction;

public class UpdateBuildingWithRequestFunction implements BiFunction<Building, PatchBuildingRequest, Building> {
    public Building apply(Building entity, PatchBuildingRequest request) {
        return Building.builder()
                .id(entity.getId())
                .name(entity.getName())
                .number(request.getNumber())
                .area(entity.getArea())
                .maximumOccupancy(request.getMaximumOccupancy())
                .occupant(entity.getOccupant())
                .build();
    }

}
