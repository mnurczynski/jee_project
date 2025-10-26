package pl.edu.pg.eti.kask.rpg.building.model;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.building.entity.Type;

import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents single organizationalUnit to be displayed or selected.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class OrganizationalUnitModel {

    String name;
    int required_occupancy;
    Type type;
    UUID id;
}
