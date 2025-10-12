package pl.edu.pg.eti.kask.rpg.building.repository.api;

import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.repository.api.Repository;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BuildingRepository extends Repository<Building, UUID> {

    Optional<Building> findByIdAndUser(UUID id, User user);


    Optional<List<Building>> findAllByUser(User user);


    Optional<List<Building>> findAllByOrganizationalUnit(OrganizationalUnit organizationalUnit);



}
