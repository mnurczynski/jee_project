package pl.edu.pg.eti.kask.rpg.building.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class OrganizationalUnitPersistenceRepository implements OrganizationalUnitRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<OrganizationalUnit> find(UUID id) {
        return Optional.ofNullable(em.find(OrganizationalUnit.class, id));
    }

    @Override
    public List<OrganizationalUnit> findAll() {
        return em.createQuery("select o from OrganizationalUnit o", OrganizationalUnit.class).getResultList();
    }

    @Override
    public void create(OrganizationalUnit entity) {
        em.persist(entity);
    }

    @Override
    public void delete(OrganizationalUnit entity) {
        em.remove(em.find(OrganizationalUnit.class, entity.getId()));
    }

    @Override
    public void update(OrganizationalUnit entity) {
        em.merge(entity);
    }
}
