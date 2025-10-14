package pl.edu.pg.eti.kask.rpg.user.repository.memory;

import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.InternalServerException;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.rpg.datastore.component.DataStore;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.nio.file.*;

public class UserInMemoryRepository implements UserRepository {
    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    private final String userAvatarPath;


    /**
     * @param store data store
     */
    public UserInMemoryRepository(DataStore store, String userAvatarPath) {
        this.store = store;
        this.userAvatarPath = userAvatarPath;
    }

    @Override
    public Optional<User> find(UUID id) {
        return store.findAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(User entity) {
        store.updateUser(entity);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return store.findAllUsers().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
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
            throw new InternalServerException(e);
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
                throw new InternalServerException(e);
            }
        }
        throw new NotFoundException();
    }

}
