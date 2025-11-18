package pl.edu.pg.eti.kask.rpg.building.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutBuildingRequest {
    private UUID organizationalUnit;
    private int number;
    private String name;
    private double area;
    private int maximumOccupancy;
}
