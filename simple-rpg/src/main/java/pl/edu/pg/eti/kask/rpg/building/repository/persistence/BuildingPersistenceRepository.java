package pl.edu.pg.eti.kask.rpg.building.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.repository.api.BuildingRepository;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class BuildingPersistenceRepository implements BuildingRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Building> find(UUID id) {
        return  Optional.ofNullable(em.find(Building.class, id));
    }

    @Override
    public List<Building> findAll() {
        return em.createQuery("select b from Building b", Building.class).getResultList();
    }

    @Override
    public void create(Building entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Building entity) {
        em.remove(em.find(Building.class, entity.getId()));
    }

    @Override
    public void update(Building entity) {
        em.merge(entity);
    }

    @Override
    public Optional<Building> findByIdAndUser(UUID id, User user) {
        try {
            return Optional.of(em.createQuery("select b from Building b where b.id = :id and b.buildingAdministrator = :user", Building.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<List<Building>> findAllByUser(User user) {
        return Optional.of(em.createQuery("select b from Building b where b.buildingAdministrator = :user", Building.class)
                .setParameter("user", user)
                .getResultList());

    }

    @Override
    public Optional<List<Building>> findAllByOrganizationalUnit(OrganizationalUnit organizationalUnit, User user) {
        return Optional.of(em.createQuery("select b from Building b where b.occupant = :occupant and b.buildingAdministrator = :admin", Building.class)
                .setParameter("occupant", organizationalUnit)
                .setParameter("admin", user)
                .getResultList());

    }

    @Override
    public Optional<List<Building>> findAllByOrganizationalUnitAdmin(OrganizationalUnit organizationalUnit) {
        return Optional.of(em.createQuery("select b from Building b where b.occupant = :occupant", Building.class)
                .setParameter("occupant", organizationalUnit)
                .getResultList());

    }
}
