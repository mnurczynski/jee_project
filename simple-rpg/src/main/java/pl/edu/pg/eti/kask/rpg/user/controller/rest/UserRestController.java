package pl.edu.pg.eti.kask.rpg.user.controller.rest;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.user.controller.api.UserController;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.rpg.user.entity.Type;
import pl.edu.pg.eti.kask.rpg.user.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Path("")
@PermitAll
public class UserRestController implements UserController {

    private final UserService userService;

    private final DtoFunctionFactory factory;

    @Inject
    public UserRestController(UserService userService, DtoFunctionFactory factory) {
        this.userService = userService;
        this.factory = factory;
    }

    @Override
    @RolesAllowed(Type.MANAGER)
    public BufferedImage getUserAvatar(UUID id) {
        var avatar = userService.getUserAvatar(id);
        if(avatar.isPresent())
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(avatar.get());
            try {
                return ImageIO.read(bais);
            } catch (IOException e) {
                throw new WebApplicationException(e);
            }
        }
        throw new NotFoundException();
    }

    @Override
    @RolesAllowed(Type.MANAGER)
    public void putUserAvatar(UUID id, BufferedImage avatar) {
        var os = new ByteArrayOutputStream();
        try {
            ImageIO.write(avatar, "png", os);
            var fix = new ByteArrayInputStream(os.toByteArray());
            userService.saveUserAvatar(id, fix.readAllBytes());
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }

    }

    @Override
    @RolesAllowed(Type.MANAGER)
    public void deleteUserAvatar(UUID id) {
        userService.deleteUserAvatar(id);
    }

    @Override
    @RolesAllowed(Type.MANAGER)
    public GetUserResponse getUser(UUID id) {
        return userService.find(id).map(factory.userToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed(Type.MANAGER)
    public GetUsersResponse getUsers() {
        return userService.findAll().map(factory.usersToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    @PermitAll
    public void putUser(UUID id, PutUserRequest request) {
        userService.create(factory.requestToUser().apply(id, request));
    }


}
