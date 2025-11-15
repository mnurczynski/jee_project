package pl.edu.pg.eti.kask.rpg.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name="users")
public class User implements Serializable {

    @Id
    UUID id;
    String login;
    String type;
    LocalDate hiringDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "buildingAdministrator",  cascade = CascadeType.REMOVE)
    List<Building> buildings;

    @ToString.Exclude
    String hashedPassword;

    public void setPassword(String password) {
        hashedPassword = password;
    }

    public String getPassword() {
        return hashedPassword;
    }
}
