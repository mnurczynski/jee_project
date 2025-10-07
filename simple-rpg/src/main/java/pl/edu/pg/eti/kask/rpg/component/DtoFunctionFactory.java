package pl.edu.pg.eti.kask.rpg.component;

import pl.edu.pg.eti.kask.rpg.character.dto.GetCharacterResponse;
import pl.edu.pg.eti.kask.rpg.character.dto.GetCharactersResponse;
import pl.edu.pg.eti.kask.rpg.character.dto.GetProfessionResponse;
import pl.edu.pg.eti.kask.rpg.character.dto.GetProfessionsResponse;
import pl.edu.pg.eti.kask.rpg.character.dto.PutCharacterRequest;
import pl.edu.pg.eti.kask.rpg.character.dto.function.CharacterToResponseFunction;
import pl.edu.pg.eti.kask.rpg.character.dto.function.CharactersToResponseFunction;
import pl.edu.pg.eti.kask.rpg.character.dto.function.ProfessionToResponseFunction;
import pl.edu.pg.eti.kask.rpg.character.dto.function.ProfessionsToResponseFunction;
import pl.edu.pg.eti.kask.rpg.character.dto.function.RequestToCharacterFunction;
import pl.edu.pg.eti.kask.rpg.character.dto.function.UpdateCharacterWithRequestFunction;
import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.rpg.user.dto.function.RequestToUserFunction;
import pl.edu.pg.eti.kask.rpg.user.dto.function.UpdateUserPasswordWithRequestFunction;
import pl.edu.pg.eti.kask.rpg.user.dto.function.UpdateUserWithRequestFunction;
import pl.edu.pg.eti.kask.rpg.user.dto.function.UserToResponseFunction;
import pl.edu.pg.eti.kask.rpg.user.dto.function.UsersToResponseFunction;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
public class DtoFunctionFactory {

    /**
     * Returns a function to convert a single {@link Character} to {@link GetCharacterResponse}.
     *
     * @return CharacterToResponseFunction instance
     */
    public CharacterToResponseFunction characterToResponse() {
        return new CharacterToResponseFunction();
    }

    /**
     * Returns a function to convert a list of {@link Character} to {@link GetCharactersResponse}.
     *
     * @return CharactersToResponseFunction instance
     */
    public CharactersToResponseFunction charactersToResponse() {
        return new CharactersToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link Profession} to {@link GetProfessionResponse}.
     *
     * @return ProfessionToResponseFunction instance
     */
    public ProfessionToResponseFunction professionToResponse() {
        return new ProfessionToResponseFunction();
    }

    /**
     * Returns a function to convert a list of {@link Profession} to {@link GetProfessionsResponse}.
     *
     * @return ProfessionsToResponseFunction instance
     */
    public ProfessionsToResponseFunction professionsToResponse() {
        return new ProfessionsToResponseFunction();
    }

    /**
     * Returns a function to convert a {@link PutCharacterRequest} to a {@link Character}.
     *
     * @return RequestToCharacterFunction instance
     */
    public RequestToCharacterFunction requestToCharacter() {
        return new RequestToCharacterFunction();
    }

    /**
     * Returns a function to update a {@link Character}.
     *
     * @return UpdateCharacterFunction instance
     */
    public UpdateCharacterWithRequestFunction updateCharacter() {
        return new UpdateCharacterWithRequestFunction();
    }

    /**
     * Returns a function to convert a {@link PutUserRequest} to a {@link User}.
     *
     * @return RequestToUserFunction instance
     */
    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    /**
     * Returns a function to update a {@link User}.
     *
     * @return UpdateUserFunction instance
     */
    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }

    /**
     * Returns a function to update a {@link User}'s password.
     *
     * @return UpdateUserPasswordFunction instance
     */
    public UpdateUserPasswordWithRequestFunction updateUserPassword() {
        return new UpdateUserPasswordWithRequestFunction();
    }

    /**
     * Returns a function to convert a list of {@link User} to {@link GetUsersResponse}.
     *
     * @return UsersToResponseFunction instance
     */
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link User} to {@link GetUserResponse}.
     *
     * @return UserToResponseFunction instance
     */
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

}
