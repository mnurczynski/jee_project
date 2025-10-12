package pl.edu.pg.eti.kask.rpg.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.user.entity.Type;

import java.time.LocalDate;
import java.util.UUID;

/**
 * GET user response. Contains only fields which can be displayed on frontend. User is defined in
 * {@link pl.edu.pg.eti.kask.rpg.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {

    UUID id;
    String login;
    Type type;
    LocalDate hiringDate;

}
