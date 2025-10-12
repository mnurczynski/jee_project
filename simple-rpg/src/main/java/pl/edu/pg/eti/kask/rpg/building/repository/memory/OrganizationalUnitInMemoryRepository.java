package pl.edu.pg.eti.kask.rpg.building.repository.memory;

import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;
import pl.edu.pg.eti.kask.rpg.datastore.component.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrganizationalUnitInMemoryRepository implements OrganizationalUnitRepository {
    private final DataStore store;

    public OrganizationalUnitInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<OrganizationalUnit> find(UUID id) {
        return Optional.of((OrganizationalUnit) store.findAllOrganizationalUnits().stream().filter(organizationalUnit -> organizationalUnit.getId().equals(id)).toList().get(0));
    }

    @Override
    public List<OrganizationalUnit> findAll() {
        return store.findAllOrganizationalUnits();
    }

    @Override
    public void create(OrganizationalUnit entity) {
        store.createOrganizationalUnit(entity);
    }

    @Override
    public void delete(OrganizationalUnit entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

    @Override
    public void update(OrganizationalUnit entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }
}
