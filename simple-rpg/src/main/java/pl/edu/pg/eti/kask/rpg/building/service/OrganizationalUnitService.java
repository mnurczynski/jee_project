package pl.edu.pg.eti.kask.rpg.building.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
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

    void checkUserLoggedIn() {
        if(securityContext.isCallerInRole(Type.READ_ONLY_USER)) {
            return;
        }
        if(securityContext.isCallerInRole(Type.MANAGER)) {
            return;
        }
        if(securityContext.isCallerInRole(Type.BUILDING_ADMINISTRATOR)) {
            return;
        }
        throw new NotAuthorizedException("");
    }

    void checkUserIsManager() {
        if(securityContext.isCallerInRole(Type.MANAGER))
        {
            return;
        };
        throw new NotAuthorizedException("");
    }


    @Inject
    public OrganizationalUnitService(SecurityContext securityContext, UserRepository userRepository, BuildingRepository buildingRepository, OrganizationalUnitRepository organizationalUnitRepository) {
        this.securityContext = securityContext;
        this.userRepository = userRepository;
        this.buildingRepository = buildingRepository;
        this.organizationalUnitRepository = organizationalUnitRepository;
    }

    public Optional<OrganizationalUnit> find(UUID id) {
        checkUserLoggedIn();
        return organizationalUnitRepository.find(id);
    }

    public Optional<List<OrganizationalUnit>> findAll() {
        checkUserLoggedIn();
        return Optional.ofNullable(organizationalUnitRepository.findAll());
    }

    @Transactional
    public void create(OrganizationalUnit unit)
    {
        checkUserIsManager();
        organizationalUnitRepository.create(unit);
    }

    @Transactional
    public void update(OrganizationalUnit unit)
    {
        checkUserIsManager();
        organizationalUnitRepository.update(unit);
    }

    @Transactional
    public void delete(UUID id)
    {
        checkUserIsManager();
        var unit = find(id);
        unit.ifPresent(organizationalUnit -> organizationalUnitRepository.delete(organizationalUnit));

    }
}
