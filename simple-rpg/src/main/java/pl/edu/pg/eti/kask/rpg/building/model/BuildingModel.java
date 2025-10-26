package pl.edu.pg.eti.kask.rpg.building.model;

import lombok.*;

import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents single building to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BuildingModel {


    private String organizationalUnit;

    private int number;
    private String name;
    private double area;
    private int maximumOccupancy;

    private UUID id;

}
