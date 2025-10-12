package pl.edu.pg.eti.kask.rpg.user.dto.function;

import pl.edu.pg.eti.kask.rpg.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.function.BiFunction;

/**
 * Returns new instance of {@link User} based on provided value and updated with values from {@link PatchUserRequest}.
 */
public class UpdateUserWithRequestFunction implements BiFunction<User, PatchUserRequest, User> {

    @Override
    public User apply(User entity, PatchUserRequest request) {
        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .hiringDate(entity.getHiringDate())
                .type(request.getType())
                .build();
    }

}
