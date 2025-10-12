package pl.edu.pg.eti.kask.rpg.building.service;

import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.repository.api.BuildingRepository;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BuildingService {

    private final UserRepository userRepository;

    private final BuildingRepository buildingRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;


    public BuildingService(UserRepository userRepository, BuildingRepository buildingRepository, OrganizationalUnitRepository organizationalUnitRepository) {
        this.userRepository = userRepository;
        this.buildingRepository = buildingRepository;
        this.organizationalUnitRepository = organizationalUnitRepository;
    }

    public Optional<Building> find(UUID id) {
        return buildingRepository.find(id);
    }

    public Optional<Building> find(User user, UUID id) {
        return buildingRepository.findByIdAndUser(id, user);
    }

    public Optional<List<Building>> findAll() {
        return Optional.ofNullable(buildingRepository.findAll());
    }

    public Optional<List<Building>> findAllByUser(UUID id) {
        return buildingRepository.findAllByUser(userRepository.find(id).get());
    }

    public Optional<List<Building>> findAllByOrganizationalUnit(UUID id) { return buildingRepository.findAllByOrganizationalUnit(organizationalUnitRepository.find(id).get());}

    public void create(Building building)
    {
        buildingRepository.create(building);
    }

    public void update(Building building)
    {
        buildingRepository.update(building);
    }

    public void delete(Building building)
    {
        buildingRepository.delete(building);
    }
}
