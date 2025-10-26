package pl.edu.pg.eti.kask.rpg.building.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;
import pl.edu.pg.eti.kask.rpg.datastore.component.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class OrganizationalUnitInMemoryRepository implements OrganizationalUnitRepository {
    private final DataStore store;

    @Inject
    public OrganizationalUnitInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<OrganizationalUnit> find(UUID id) {
        return  store.findAllOrganizationalUnits().stream().filter(organizationalUnit -> organizationalUnit.getId().equals(id)).findFirst();
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
        store.deleteOrganizationalUnit(entity.getId());
    }

    @Override
    public void update(OrganizationalUnit entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }
}
