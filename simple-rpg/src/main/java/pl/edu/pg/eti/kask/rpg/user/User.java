package pl.edu.pg.eti.kask.rpg.user;

import pl.edu.pg.eti.kask.rpg.building.Building;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class User implements Serializable {
    String login;
    Type type;
    LocalDate hiringDate;
    List<Building> buildings;

}
