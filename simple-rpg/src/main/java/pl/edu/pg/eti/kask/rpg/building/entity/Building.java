package pl.edu.pg.eti.kask.rpg.building.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name="buildings")
public class Building implements Serializable {

    @Id
    UUID id;
    int number;
    String name;
    double area;
    int maximumOccupancy;

    @ManyToOne
    @JoinColumn(name = "admin")
    User buildingAdministrator;

    @ManyToOne
    @JoinColumn(name = "occupant")
    OrganizationalUnit occupant;
}
