package pl.edu.pg.eti.kask.rpg.building.model;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents new building to be created. Includes oll
 * fields which can be used in building creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BuildingCreateModel {

    /**
     * Building's id.
     */
    private UUID id;
    private int number;

    private double area;
    private int maximumOccupancy;
    private User buildingAdministrator;
    /**
     * Name of the building.
     */
    private String name;

    /**
     * Building's organizationalUnit.
     */
    private OrganizationalUnitModel organizationalUnit;

}
