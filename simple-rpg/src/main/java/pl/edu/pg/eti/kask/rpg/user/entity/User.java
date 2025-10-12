package pl.edu.pg.eti.kask.rpg.user.entity;

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
public class User implements Serializable {

    UUID id;
    String login;
    Type type;
    LocalDate hiringDate;
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
