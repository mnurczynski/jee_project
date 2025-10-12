package pl.edu.pg.eti.kask.rpg.building.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchBuildingRequest {


    private int maximumOccupancy;

    private int number;
}
