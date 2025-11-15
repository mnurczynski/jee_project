package pl.edu.pg.eti.kask.rpg.building.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.repository.api.BuildingRepository;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;
import pl.edu.pg.eti.kask.rpg.user.entity.Type;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
@RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
public class OrganizationalUnitService {

    private final SecurityContext securityContext;
    private final UserRepository userRepository;

    private final BuildingRepository buildingRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;

    @Inject
    public OrganizationalUnitService(SecurityContext securityContext, UserRepository userRepository, BuildingRepository buildingRepository, OrganizationalUnitRepository organizationalUnitRepository) {
        this.securityContext = securityContext;
        this.userRepository = userRepository;
        this.buildingRepository = buildingRepository;
        this.organizationalUnitRepository = organizationalUnitRepository;
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public Optional<OrganizationalUnit> find(UUID id) {
        return organizationalUnitRepository.find(id);
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public Optional<List<OrganizationalUnit>> findAll() {
        return Optional.ofNullable(organizationalUnitRepository.findAll());
    }

    @Transactional
    @RolesAllowed(Type.MANAGER)
    public void create(OrganizationalUnit unit)
    {
        organizationalUnitRepository.create(unit);
    }

    @Transactional
    @RolesAllowed(Type.MANAGER)
    public void update(OrganizationalUnit unit)
    {
        organizationalUnitRepository.update(unit);
    }

    @Transactional
    @RolesAllowed(Type.MANAGER)
    public void delete(UUID id)
    {
        var unit = find(id);
        unit.ifPresent(organizationalUnit -> organizationalUnitRepository.delete(organizationalUnit));

    }
}
