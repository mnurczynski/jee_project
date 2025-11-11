package pl.edu.pg.eti.kask.rpg.building.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.repository.api.BuildingRepository;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class BuildingService {

    private final UserRepository userRepository;

    private final BuildingRepository buildingRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;

    @Inject
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
        var user = userRepository.find(id);
        if (user.isPresent()) {
            return buildingRepository.findAllByUser(user.get());
        }
        throw new NotFoundException("User not found");
    }

    public Optional<List<Building>> findAllByOrganizationalUnit(UUID id) {
        var unit = organizationalUnitRepository.find(id);
        if(unit.isPresent()) {
            return buildingRepository.findAllByOrganizationalUnit(unit.get());
        }
        throw new NotFoundException("Organizational unit not found");
    }

    @Transactional
    public void create(Building building)
    {
        if (buildingRepository.find(building.getId()).isPresent()) {
            throw new IllegalArgumentException("Building already exists.");
        }
        if (organizationalUnitRepository.find(building.getOccupant().getId()).isEmpty()) {
            throw new IllegalArgumentException("Unit does not exists.");
        }
        buildingRepository.create(building);
    }

    @Transactional
    public void update(Building building)
    {
        buildingRepository.update(building);
    }

    @Transactional
    public void delete(UUID id)
    {
        var building = find(id);
        if(building.isPresent()) {
            buildingRepository.delete(building.get());
        }

    }
}
