package pl.edu.pg.eti.kask.rpg.building.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name="organizational_units")
public class OrganizationalUnit implements Serializable {
    @Id
    UUID id;
    String name;
    int required_occupancy;
    Type type;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "occupant", cascade = CascadeType.REMOVE)
    List<Building> buildingsOccupied;
}
