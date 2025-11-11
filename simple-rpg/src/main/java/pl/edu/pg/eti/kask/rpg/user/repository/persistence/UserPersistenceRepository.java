package pl.edu.pg.eti.kask.rpg.user.repository.persistence;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UserPersistenceRepository implements UserRepository {
    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private EntityManager em;

    @Resource(name = "USER_AVATAR_PATH")
    private String userAvatarPath;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(em.find(User.class, entity.getId()));
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            return Optional.of(em.createQuery("select u from User u where u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult());
        } catch(NoResultException e) {
            return Optional.empty();
        }
    }

    private Path getUserAvatarPath(UUID id) {
        return Paths.get(userAvatarPath, id.toString());
    }

    @Override
    public Optional<byte[]> findAvatar(UUID id) {
        Path userAvatarPath = getUserAvatarPath(id);
        if(Files.exists(userAvatarPath))
        {
            try {
                InputStream stream = new FileInputStream(userAvatarPath.toFile());
                return Optional.of(stream.readAllBytes());

            } catch (IOException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public void saveAvatar(UUID id, byte[] avatar) {
        try
        {
            deleteAvatar(id);
        }
        catch(NotFoundException ignored)
        {

        }
        Path userAvatarPath = getUserAvatarPath(id);
        try {
            if(!Files.exists(Path.of(this.userAvatarPath)))
            {
                Files.createDirectories(Path.of(this.userAvatarPath));
            }
            Files.createFile(userAvatarPath);
            Files.write(userAvatarPath, avatar);
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
    }

    @Override
    public void deleteAvatar(UUID id) {
        Path userAvatarPath = getUserAvatarPath(id);
        if(Files.exists(userAvatarPath))
        {
            try {
                Files.delete(userAvatarPath);
                return;
            } catch (IOException e) {
                throw new WebApplicationException(e);
            }
        }
        throw new NotFoundException();
    }

}
