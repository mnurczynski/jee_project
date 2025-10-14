package pl.edu.pg.eti.kask.rpg.building.repository.memory;

import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.repository.api.BuildingRepository;
import pl.edu.pg.eti.kask.rpg.datastore.component.DataStore;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BuildingInMemoryRepository implements BuildingRepository {

    private final DataStore store;

    public BuildingInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Building> find(UUID id) {
        return  store.findAllBuildings().stream().filter(building -> building.getId().equals(id)).findFirst();
    }

    @Override
    public List<Building> findAll() {
        return store.findAllBuildings();
    }

    @Override
    public void create(Building entity) {
        store.createBuildings(entity);
    }

    @Override
    public void delete(Building entity) {
        store.deleteBuilding(entity.getId());
    }

    @Override
    public void update(Building entity) {
        store.updateBuilding(entity);
    }

    @Override
    public Optional<Building> findByIdAndUser(UUID id, User user) {
        return store.findAllBuildings().stream().filter(building -> building.getId().equals(id)).filter(building -> building.getBuildingAdministrator().equals(user)).findFirst();
    }

    @Override
    public Optional<List<Building>> findAllByUser(User user) {
        return Optional.of(store.findAllBuildings().stream().filter(building -> building.getBuildingAdministrator().equals(user)).toList());
    }

    @Override
    public Optional<List<Building>> findAllByOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        return Optional.of(store.findAllBuildings().stream().filter(building -> building.getOccupant().equals(organizationalUnit)).toList());
    }
}
