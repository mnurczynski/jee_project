package pl.edu.pg.eti.kask.rpg.datastore.component;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
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
public class DataStore {

    /**
     * Set of all available professions.
     */
    private final Set<Profession> professions = new HashSet<>();

    /**
     * Set of all characters.
     */
    private final Set<Character> characters = new HashSet<>();

    /**
     * Set of all users.
     */
    private final Set<User> users = new HashSet<>();

    /**
     * Component used for creating deep copies.
     */
    private final CloningUtility cloningUtility;

    /**
     * @param cloningUtility component used for creating deep copies
     */
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    /**
     * Seeks for all professions.
     *
     * @return list (can be empty) of all professions
     */
    public synchronized List<Profession> findAllProfessions() {
        return professions.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new profession.
     *
     * @param value new profession to be stored
     * @throws IllegalArgumentException if profession with provided id already exists
     */
    public synchronized void createProfession(Profession value) throws IllegalArgumentException {
        if (professions.stream().anyMatch(profession -> profession.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The profession id \"%s\" is not unique".formatted(value.getId()));
        }
        professions.add(cloningUtility.clone(value));
    }

    /**
     * Seeks for all characters.
     *
     * @return list (can be empty) of all characters
     */
    public synchronized List<Character> findAllCharacters() {
        return characters.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new character.
     *
     * @param value new character to be stored
     * @throws IllegalArgumentException if character with provided id already exists or when {@link User} or
     *                                  {@link Profession} with provided uuid does not exist
     */
    public synchronized void createCharacter(Character value) throws IllegalArgumentException {
        if (characters.stream().anyMatch(character -> character.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The character id \"%s\" is not unique".formatted(value.getId()));
        }
        Character entity = cloneWithRelationships(value);
        characters.add(entity);
    }

    /**
     * Updates existing character.
     *
     * @param value character to be updated
     * @throws IllegalArgumentException if character with the same id does not exist or when {@link User} or
     *                                  {@link Profession} with provided uuid does not exist
     */
    public synchronized void updateCharacter(Character value) throws IllegalArgumentException {
        Character entity = cloneWithRelationships(value);
        if (characters.removeIf(character -> character.getId().equals(value.getId()))) {
            characters.add(entity);
        } else {
            throw new IllegalArgumentException("The character with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Deletes existing character.
     *
     * @param id id of character to be deleted
     * @throws IllegalArgumentException if character with provided id does not exist
     */
    public synchronized void deleteCharacter(UUID id) throws IllegalArgumentException {
        if (!characters.removeIf(character -> character.getId().equals(id))) {
            throw new IllegalArgumentException("The character with id \"%s\" does not exist".formatted(id));
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
        if (users.stream().anyMatch(character -> character.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
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
        if (users.removeIf(character -> character.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Clones existing character and updates relationships for values in storage
     *
     * @param value character
     * @return cloned value with updated relationships
     * @throws IllegalArgumentException when {@link User} or {@link Profession} with provided uuid does not exist
     */
    private Character cloneWithRelationships(Character value) {
        Character entity = cloningUtility.clone(value);

        if (entity.getUser() != null) {
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }

        if (entity.getProfession() != null) {
            entity.setProfession(professions.stream()
                    .filter(profession -> profession.getId().equals(value.getProfession().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No profession with id \"%s\".".formatted(value.getProfession().getId()))));
        }

        return entity;
    }

}
