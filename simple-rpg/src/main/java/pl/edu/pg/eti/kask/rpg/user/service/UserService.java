package pl.edu.pg.eti.kask.rpg.user.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.rpg.user.entity.Type;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserService {

    private final SecurityContext securityContext;
    private final Pbkdf2PasswordHash passwordHash;
    private final UserRepository repository;

    /**
     * Hash mechanism used for storing users' passwords.
     */

    /**
     * @param repository   repository for building entity
     * @param passwordHash hash mechanism used for storing users' passwords
     */
    @Inject
    public UserService(SecurityContext securityContext, UserRepository repository, Pbkdf2PasswordHash passwordHash) {
        this.securityContext = securityContext;
        this.repository = repository;
        this.passwordHash = passwordHash;
    }


    @Transactional
    @RolesAllowed(Type.MANAGER)
    public void saveUserAvatar(UUID id, byte [] avatar)
    {
        repository.saveAvatar(id, avatar);
    }

    @RolesAllowed(Type.MANAGER)
    public Optional<byte []> getUserAvatar(UUID id)
    {
        return repository.findAvatar(id);
    }

    @Transactional
    @RolesAllowed(Type.MANAGER)
    public void deleteUserAvatar(UUID id)
    {
        repository.deleteAvatar(id);
    }
    /**
     * @param id user's id
     * @return container (can be empty) with user
     */
    @RolesAllowed(Type.MANAGER)
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    /**
     * Seeks for single user using login and password. Can be used in authentication module.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    @RolesAllowed(Type.MANAGER)
    public Optional<User> find(String login) {
        return repository.findByLogin(login);
    }

    @RolesAllowed(Type.MANAGER)
    public Optional<List<User>> findAll() {return Optional.of(repository.findAll());}

    /**
     * Saves new user. Password is hashed using configured hash algorithm.
     *
     * @param user new user to be saved
     */
    @Transactional
    @PermitAll
    public void create(User user) {
        if (repository.find(user.getId()).isPresent()) {
            throw new IllegalArgumentException("User already exists.");
        }
        assert passwordHash != null;
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }

    /**
     * @param login    user's login
     * @param password user's password
     * @return true if provided login and password are correct
     */
    @PermitAll
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

}
