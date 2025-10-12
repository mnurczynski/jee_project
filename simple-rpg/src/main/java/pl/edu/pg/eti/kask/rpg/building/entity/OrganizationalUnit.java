package pl.edu.pg.eti.kask.rpg.building.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class OrganizationalUnit implements Serializable {
    UUID id;
    String name;
    int required_occupancy;
    Type type;

    List<Building> buildingsOccupied;
}
