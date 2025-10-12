package pl.edu.pg.eti.kask.rpg.building.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.building.entity.Type;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetOrganizationalUnitResponse {

    String name;
    int required_occupancy;
    Type type;
    UUID id;
}
