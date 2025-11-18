package pl.edu.pg.eti.kask.rpg.user.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * PUT user request. Contains only fields that can be set during user creation. User is defined in
 * {@link pl.edu.pg.eti.kask.rpg.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutUserRequest {

    String login;
    String type;
    String password;
    LocalDate hiringDate;




}
