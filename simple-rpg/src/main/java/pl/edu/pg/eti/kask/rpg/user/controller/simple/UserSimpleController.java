package pl.edu.pg.eti.kask.rpg.user.controller.simple;

import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.user.controller.api.UserController;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.rpg.user.service.UserService;

import java.util.UUID;

public class UserSimpleController implements UserController {

    private final UserService userService;

    private final DtoFunctionFactory factory;

    public UserSimpleController(UserService userService, DtoFunctionFactory factory) {
        this.userService = userService;
        this.factory = factory;
    }

    @Override
    public byte[] getUserAvatar(UUID id) {
        return new byte[0];
    }

    @Override
    public void putUserAvatar(UUID id, byte[] byteStream) {

    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return null;
    }

    @Override
    public GetUsersResponse getUsers() {
        return null;
    }
}
