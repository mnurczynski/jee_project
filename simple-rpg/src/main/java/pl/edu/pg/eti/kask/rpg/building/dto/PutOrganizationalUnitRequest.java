package pl.edu.pg.eti.kask.rpg.building.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.building.entity.Type;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutOrganizationalUnitRequest {
    String name;
    int required_occupancy;
    Type type;

}
