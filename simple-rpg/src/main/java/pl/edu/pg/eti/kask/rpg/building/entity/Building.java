package pl.edu.pg.eti.kask.rpg.building.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class Building implements Serializable {

    UUID id;
    int number;
    String name;
    double area;
    int maximumOccupancy;
    User buildingAdministrator;
    OrganizationalUnit occupant;
}
