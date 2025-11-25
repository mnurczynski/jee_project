package pl.edu.pg.eti.kask.rpg.building.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Building} based on provided value and updated with values from
 * {@link BuildingEditModel}.
 */
public class UpdateBuildingWithModelFunction implements BiFunction<Building, BuildingEditModel, Building>, Serializable {

    @Override
    @SneakyThrows
    public Building apply(Building entity, BuildingEditModel request) {
        return Building.builder()
                .id(entity.getId())
                .name(entity.getName())
                .number(request.getNumber())
                .area(entity.getArea())
                .maximumOccupancy(request.getMaximumOccupancy())
                .buildingAdministrator(entity.getBuildingAdministrator())
                .occupant(OrganizationalUnit.builder()
                        .id(entity.getOccupant().getId())
                        .build())
                .build();
    }

}
