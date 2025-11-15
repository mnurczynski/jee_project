package pl.edu.pg.eti.kask.rpg.user.dto.function;

import pl.edu.pg.eti.kask.rpg.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutUserRequest} to {@link User}. Caution, password should be hashed in business logic.
 */
public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {

    @Override
    public User apply(UUID id, PutUserRequest request) {
        return User.builder()
                .id(id)
                .login(request.getLogin())
                .hiringDate(request.getHiringDate())
                .type(request.getType())
                .build();
    }

}
