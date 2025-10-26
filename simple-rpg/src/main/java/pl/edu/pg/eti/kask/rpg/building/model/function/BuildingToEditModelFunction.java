package pl.edu.pg.eti.kask.rpg.building.model.function;

import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingEditModel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link Building} to {@link BuildingEditModel}.
 */
public class BuildingToEditModelFunction implements Function<Building, BuildingEditModel>, Serializable {

    @Override
    public BuildingEditModel apply(Building entity) {
        return BuildingEditModel.builder()
                .maximumOccupancy(entity.getMaximumOccupancy())
                .number(entity.getNumber())
                .name(entity.getName())
                .build();
    }

}
