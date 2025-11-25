package pl.edu.pg.eti.kask.rpg.user.model.function;

import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.model.UsersModel;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<User>} to {@link UsersModel}.
 */
public class UsersToModelFunction implements Function<List<User>, UsersModel> {

    @Override
    public UsersModel apply(List<User> entity) {
        return UsersModel.builder()
                .users(entity.stream()
                        .map(building -> UsersModel.User.builder()
                                .id(building.getId())
                                .login(building.getLogin())
                                .build())
                        .toList())
                .build();
    }

}
