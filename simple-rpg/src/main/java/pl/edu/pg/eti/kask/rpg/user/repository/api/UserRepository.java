package pl.edu.pg.eti.kask.rpg.user.repository.api;

import pl.edu.pg.eti.kask.rpg.repository.api.Repository;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {

    /**
     * Seeks for single user using login.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    Optional<User> findByLogin(String login);


    Optional<byte[]> findAvatar(UUID id);


    void saveAvatar(UUID id, byte[] avatar);

    void deleteAvatar(UUID id);
}

