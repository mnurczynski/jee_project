package pl.edu.pg.eti.kask.rpg.user.dto.function;

import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.function.Function;

/**
 * Converts {@link User} to {@link GetUserResponse}.
 */
public class UserToResponseFunction implements Function<User, GetUserResponse> {

    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .hiringDate(user.getHiringDate())
                .type(user.getType())
                .build();
    }

}
