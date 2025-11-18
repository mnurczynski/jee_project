package pl.edu.pg.eti.kask.rpg.building.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.repository.api.BuildingRepository;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;
import pl.edu.pg.eti.kask.rpg.user.entity.Type;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
@RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
public class BuildingService {


    private final SecurityContext securityContext;
    private final UserRepository userRepository;

    private final BuildingRepository buildingRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;

    @Inject
    public BuildingService(SecurityContext securityContext, UserRepository userRepository, BuildingRepository buildingRepository, OrganizationalUnitRepository organizationalUnitRepository) {
        this.securityContext = securityContext;
        this.userRepository = userRepository;
        this.buildingRepository = buildingRepository;
        this.organizationalUnitRepository = organizationalUnitRepository;
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public Optional<Building> find(UUID id) {
        return buildingRepository.find(id);
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public Optional<Building> find(User user, UUID id) {
        return buildingRepository.findByIdAndUser(id, user);
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public Optional<List<Building>> findAll() {
        return Optional.ofNullable(buildingRepository.findAll());
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public Optional<Building> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(Type.MANAGER) || securityContext.isCallerInRole(Type.READ_ONLY_USER)) {
            return find(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(user, id);
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public Optional<List<Building>> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(Type.MANAGER) || securityContext.isCallerInRole(Type.READ_ONLY_USER)) {
            return findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAllByUser(user.getId());
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER})
    public Optional<List<Building>> findAllByUser(UUID id) {
        var user = userRepository.find(id);
        if (user.isPresent()) {
            var u = user.get();
            var currentUserLogin = securityContext.getCallerPrincipal().getName();
            if(!(u.getLogin().equals(currentUserLogin)) && !securityContext.isCallerInRole(Type.MANAGER))
            {
                throw new ForbiddenException();
            }
            return buildingRepository.findAllByUser(user.get());
        }
        throw new NotFoundException("User not found");
    }

    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public Optional<List<Building>> findAllByOrganizationalUnit(UUID id) {
        var unit = organizationalUnitRepository.find(id);
        if(unit.isPresent()) {
            return buildingRepository.findAllByOrganizationalUnit(unit.get());
        }
        throw new NotFoundException("Organizational unit not found");
    }

    @Transactional
    @RolesAllowed(Type.MANAGER)
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
    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER})
    public void createForCallerPrincipal(Building building) {
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        building.setBuildingAdministrator(user);
        create(building);
    }


    @Transactional
    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER})
    public void update(Building building)
    {
        checkAdminRoleOrOwner(buildingRepository.find(building.getId()));
        buildingRepository.update(building);
    }

    @Transactional
    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER})
    public void delete(UUID id)
    {
        checkAdminRoleOrOwner(buildingRepository.find(id));
        var building = find(id);
        if(building.isPresent()) {
            buildingRepository.delete(building.get());
        }

    }

    private void checkAdminRoleOrOwner(Optional<Building> building) throws BadRequestException {
        if (securityContext.isCallerInRole(Type.MANAGER)) {
            return;
        }
        if (securityContext.isCallerInRole(Type.BUILDING_ADMINISTRATOR)
                && building.isPresent()
                && building.get().getBuildingAdministrator().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new ForbiddenException("Caller not authorized.");
    }

}
