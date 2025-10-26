package pl.edu.pg.eti.kask.rpg.building.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingCreateModel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link BuildingCreateModel} to {@link Building}.
 */
public class ModelToBuildingFunction implements Function<BuildingCreateModel, Building>, Serializable {

    @Override
    @SneakyThrows
    public Building apply(BuildingCreateModel model) {
        return Building.builder()
                .id(model.getId())
                .name(model.getName())
                .number(model.getNumber())
                .area(model.getArea())
                .maximumOccupancy(model.getMaximumOccupancy())
                .occupant(OrganizationalUnit.builder()
                        .id(model.getOrganizationalUnit().getId())
                        .build())
                .build();
    }

}
