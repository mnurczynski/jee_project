package pl.edu.pg.eti.kask.rpg.building;

import java.util.List;

public class OrganizationalUnit {
    String name;
    int required_occupancy;
    Type type;
    List<Building> buildingsOccupied;
}
