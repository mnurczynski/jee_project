package pl.edu.pg.eti.kask.rpg.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.user.entity.Type;

/**
 * PATCH user request. Contains only fields which can be changed byt the user while updating its profile. User is
 * defined in {@link pl.edu.pg.eti.kask.rpg.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchUserRequest {

    /**
     * User's name.
     */
    private Type type;



}
