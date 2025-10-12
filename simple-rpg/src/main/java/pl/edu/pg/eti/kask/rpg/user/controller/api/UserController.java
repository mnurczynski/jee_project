package pl.edu.pg.eti.kask.rpg.user.controller.api;

import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUsersResponse;

import java.util.UUID;

public interface UserController {

    public byte [] getUserAvatar(UUID id);

    public void putUserAvatar(UUID id, byte[] byteStream);

    public GetUserResponse getUser(UUID id);

    public GetUsersResponse getUsers();
}
