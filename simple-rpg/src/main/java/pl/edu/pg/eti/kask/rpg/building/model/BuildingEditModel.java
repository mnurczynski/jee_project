package pl.edu.pg.eti.kask.rpg.building.model;

import lombok.*;

/**
 * JSF view model class in order to not use entity classes. Represents single building to be edited. Includes
 * only fields which can be edited after building creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BuildingEditModel {

    private int maximumOccupancy;

    private int number;

    private String name;
}
