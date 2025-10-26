package pl.edu.pg.eti.kask.rpg.building.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents list of buildings to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BuildingsModel implements Serializable {

    /**
     * Represents single building in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Building {

        /**
         * Unique id identifying building.
         */
        private UUID id;

        private int number;
        /**
         * Name of the building.
         */
        private String name;

    }

    /**
     * Name of the selected buildings.
     */
    @Singular
    private List<Building> buildings;

}
