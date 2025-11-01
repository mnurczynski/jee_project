package pl.edu.pg.eti.kask.rpg.building.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchOrganizationalUnitRequest {


    private int required_occupancy;
}
