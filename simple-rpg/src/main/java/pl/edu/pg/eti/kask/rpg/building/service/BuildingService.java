package pl.edu.pg.eti.kask.rpg.building.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.repository.api.BuildingRepository;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;
import pl.edu.pg.eti.kask.rpg.logger.LogOperation;
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

    void checkUserNotWriteOnly() {
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

    public Optional<Building> find(UUID id) {
        checkUserLoggedIn();
        return buildingRepository.find(id);
    }

    public Optional<Building> find(User user, UUID id) {
        checkUserLoggedIn();
        return buildingRepository.findByIdAndUser(id, user);
    }

    public Optional<List<Building>> findAll() {
        checkUserLoggedIn();
        return Optional.ofNullable(buildingRepository.findAll());
    }

    public Optional<Building> findForCallerPrincipal(UUID id) {
        checkUserLoggedIn();
        if (securityContext.isCallerInRole(Type.MANAGER) || securityContext.isCallerInRole(Type.READ_ONLY_USER)) {
            return find(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(user, id);
    }

    public Optional<List<Building>> findAllForCallerPrincipal() {
        checkUserLoggedIn();
        if (securityContext.isCallerInRole(Type.MANAGER) || securityContext.isCallerInRole(Type.READ_ONLY_USER)) {
            return findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAllByUser(user.getId());
    }

    public Optional<List<Building>> findAllByUser(UUID id) {
        checkUserLoggedIn();
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

    public Optional<List<Building>> findAllByOrganizationalUnit(UUID id) {
        checkUserLoggedIn();
        var unit = organizationalUnitRepository.find(id);
        if (securityContext.isCallerInRole(Type.MANAGER) || securityContext.isCallerInRole(Type.READ_ONLY_USER)) {
            return buildingRepository.findAllByOrganizationalUnitAdmin(unit.get());
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        if(unit.isPresent()) {
            return buildingRepository.findAllByOrganizationalUnit(unit.get(), user);
        }
        throw new NotFoundException("Organizational unit not found");
    }

    @LogOperation
    @Transactional
    public void create(Building building)
    {
        checkUserNotWriteOnly();
        if (buildingRepository.find(building.getId()).isPresent()) {
            throw new IllegalArgumentException("Building already exists.");
        }
        if (organizationalUnitRepository.find(building.getOccupant().getId()).isEmpty()) {
            throw new IllegalArgumentException("Unit does not exists.");
        }
        buildingRepository.create(building);
    }

    @LogOperation
    @Transactional
    public void createForCallerPrincipal(Building building) {
        checkUserNotWriteOnly();
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        building.setBuildingAdministrator(user);
        create(building);
    }

    @LogOperation
    @Transactional
    public void update(Building building)
    {
        checkUserNotWriteOnly();
        checkAdminRoleOrOwner(buildingRepository.find(building.getId()));
        buildingRepository.update(building);
    }

    @LogOperation
    @Transactional
    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER})
    public void delete(UUID id)
    {
        checkUserNotWriteOnly();
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
