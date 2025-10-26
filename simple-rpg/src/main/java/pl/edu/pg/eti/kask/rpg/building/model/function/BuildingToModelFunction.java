package pl.edu.pg.eti.kask.rpg.building.model.function;

import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingModel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link Building} to {@link BuildingModel}.
 */
public class BuildingToModelFunction implements Function<Building, BuildingModel>, Serializable {

    @Override
    public BuildingModel apply(Building entity) {
        return BuildingModel.builder()
                .name(entity.getName())
                .number(entity.getNumber())
                .area(entity.getArea())
                .maximumOccupancy(entity.getMaximumOccupancy())
                .organizationalUnit(entity.getOccupant().getName())
                .build();
    }

}
