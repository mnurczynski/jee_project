package pl.edu.pg.eti.kask.rpg.building.model.function;

import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.model.OrganizationalUnitModel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link OrganizationalUnit} to {@link OrganizationalUnitModel}.
 */
public class OrganizationalUnitToModelFunction implements Function<OrganizationalUnit, OrganizationalUnitModel>, Serializable {


    @Override
    public OrganizationalUnitModel apply(OrganizationalUnit entity) {
        return OrganizationalUnitModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .required_occupancy(entity.getRequired_occupancy())
                .type(entity.getType())
                .build();
    }

}
