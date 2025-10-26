package pl.edu.pg.eti.kask.rpg.building.model.function;

import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingsModel;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<Building>} to {@link BuildingsModel}.
 */
public class BuildingsToModelFunction implements Function<List<Building>, BuildingsModel> {

    @Override
    public BuildingsModel apply(List<Building> entity) {
        return BuildingsModel.builder()
                .buildings(entity.stream()
                        .map(building -> BuildingsModel.Building.builder()
                                .id(building.getId())
                                .name(building.getName())
                                .number(building.getNumber())
                                .build())
                        .toList())
                .build();
    }

}
