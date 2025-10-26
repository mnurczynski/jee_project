package pl.edu.pg.eti.kask.rpg.building.model.function;

import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingsModel;
import pl.edu.pg.eti.kask.rpg.building.model.OrganizationalUnitsModel;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<Building>} to {@link BuildingsModel}.
 */
public class OrganizationalUnitsToModelFunction implements Function<List<OrganizationalUnit>, OrganizationalUnitsModel> {

    @Override
    public OrganizationalUnitsModel apply(List<OrganizationalUnit> entity) {
        return OrganizationalUnitsModel.builder()
                .units(entity.stream()
                        .map(unit -> OrganizationalUnitsModel.OrganizationalUnit.builder()
                                .id(unit.getId())
                                .name(unit.getName())
                                .build())
                        .toList())
                .build();
    }

}
