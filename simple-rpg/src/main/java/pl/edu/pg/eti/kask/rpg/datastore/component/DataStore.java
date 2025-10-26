package pl.edu.pg.eti.kask.rpg.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.rpg.serialization.component.CloningUtility;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * For the sake of simplification instead of using real database this example is using a data source object which should
 * be put in servlet context in a single instance. In order to avoid {@link java.util.ConcurrentModificationException}
 * all methods are synchronized. Normally synchronization would be carried on by the database server. Caution, this is
 * very inefficient implementation but can be used to present other mechanisms without obscuration example with ORM
 * usage.
 */
@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {


    private final Set<OrganizationalUnit> organizationalUnits = new HashSet<>();


    private final Set<Building> buildings = new HashSet<>();


    private final Set<User> users = new HashSet<>();


    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }


    public synchronized List<OrganizationalUnit> findAllOrganizationalUnits() {
        return organizationalUnits.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }


    public synchronized void createOrganizationalUnit(OrganizationalUnit value) throws IllegalArgumentException {
        if (organizationalUnits.stream().anyMatch(organizationalUnit -> organizationalUnit.getId().equals(value.getId()))) {
            throw new BadRequestException("The organizational unit id \"%s\" is not unique".formatted(value.getId()));
        }
        organizationalUnits.add(cloningUtility.clone(value));
    }


    public synchronized List<Building> findAllBuildings() {
        return buildings.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }


    public synchronized void createBuildings(Building value) throws IllegalArgumentException {
        if (buildings.stream().anyMatch(building -> building.getId().equals(value.getId()))) {
            throw new BadRequestException("The building id \"%s\" is not unique".formatted(value.getId()));
        }
        Building entity = cloneWithRelationships(value);
        buildings.add(entity);
    }


    public synchronized void updateBuilding(Building value) throws IllegalArgumentException {
        Building entity = cloneWithRelationships(value);
        if (buildings.removeIf(building -> building.getId().equals(value.getId()))) {
            buildings.add(entity);
        } else {
            throw new NotFoundException("The building with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Deletes existing building.
     *
     * @param id id of building to be deleted
     * @throws IllegalArgumentException if building with provided id does not exist
     */
    public synchronized void deleteBuilding(UUID id) throws IllegalArgumentException {
        if (!buildings.removeIf(building -> building.getId().equals(id))) {
            throw new NotFoundException("The building with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized void deleteOrganizationalUnit(UUID id) throws IllegalArgumentException {
        buildings.removeIf(building -> building.getOccupant().getId().equals(id));
        if (!organizationalUnits.removeIf(organizationalUnit -> organizationalUnit.getId().equals(id))) {
            throw new NotFoundException("Doesnt exist");
        }
    }

    /**
     * Seeks for all users.
     *
     * @return list (can be empty) of all users
     */
    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new user.
     *
     * @param value new user to be stored
     * @throws IllegalArgumentException if user with provided id already exists
     */
    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(building -> building.getId().equals(value.getId()))) {
            throw new BadRequestException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    /**
     * Updates existing user.
     *
     * @param value user to be updated
     * @throws IllegalArgumentException if user with the same id does not exist
     */
    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(building -> building.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new NotFoundException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }


    private Building cloneWithRelationships(Building value) {
        Building entity = cloningUtility.clone(value);

        if (entity.getBuildingAdministrator() != null) {
            entity.setBuildingAdministrator(users.stream()
                    .filter(user -> user.getId().equals(value.getBuildingAdministrator().getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("No user with id \"%s\".".formatted(value.getBuildingAdministrator().getId()))));
        }

        if (entity.getOccupant() != null) {
            entity.setOccupant(organizationalUnits.stream()
                    .filter(organizationalUnit -> organizationalUnit.getId().equals(value.getOccupant().getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("No unit with id \"%s\".".formatted(value.getOccupant().getId()))));
        }

        return entity;
    }

}
