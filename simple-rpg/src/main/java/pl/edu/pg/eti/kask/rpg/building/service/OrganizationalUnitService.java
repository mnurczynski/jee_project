package pl.edu.pg.eti.kask.rpg.building.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.repository.api.BuildingRepository;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class OrganizationalUnitService {
    private final UserRepository userRepository;

    private final BuildingRepository buildingRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;

    @Inject
    public OrganizationalUnitService(UserRepository userRepository, BuildingRepository buildingRepository, OrganizationalUnitRepository organizationalUnitRepository) {
        this.userRepository = userRepository;
        this.buildingRepository = buildingRepository;
        this.organizationalUnitRepository = organizationalUnitRepository;
    }

    public Optional<OrganizationalUnit> find(UUID id) {
        return organizationalUnitRepository.find(id);
    }


    public Optional<List<OrganizationalUnit>> findAll() {
        return Optional.ofNullable(organizationalUnitRepository.findAll());
    }


    public void create(OrganizationalUnit unit)
    {
        organizationalUnitRepository.create(unit);
    }

    public void update(OrganizationalUnit unit)
    {
        organizationalUnitRepository.update(unit);
    }

    public void delete(OrganizationalUnit unit)
    {
        organizationalUnitRepository.delete(unit);
    }
}
