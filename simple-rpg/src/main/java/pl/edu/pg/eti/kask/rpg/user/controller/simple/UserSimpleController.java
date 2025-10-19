package pl.edu.pg.eti.kask.rpg.user.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.rpg.user.controller.api.UserController;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.rpg.user.service.UserService;

import java.util.UUID;

@RequestScoped
public class UserSimpleController implements UserController {

    private final UserService userService;

    private final DtoFunctionFactory factory;

    @Inject
    public UserSimpleController(UserService userService, DtoFunctionFactory factory) {
        this.userService = userService;
        this.factory = factory;
    }

    @Override
    public byte[] getUserAvatar(UUID id) {
        var avatar = userService.getUserAvatar(id);
        if(avatar.isPresent())
        {
            return avatar.get();
        }
        throw new NotFoundException();
    }

    @Override
    public void putUserAvatar(UUID id, byte[] byteStream) {
        userService.saveUserAvatar(id, byteStream);
    }

    @Override
    public void deleteUserAvatar(UUID id) {
        userService.deleteUserAvatar(id);
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return userService.find(id).map(factory.userToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    public GetUsersResponse getUsers() {
        return userService.findAll().map(factory.usersToResponse()).orElseThrow(NotFoundException::new);
    }
}
