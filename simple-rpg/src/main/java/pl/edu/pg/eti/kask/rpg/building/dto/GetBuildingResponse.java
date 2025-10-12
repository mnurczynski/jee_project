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
public class GetBuildingResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class OrganizationalUnit{
        private UUID id;

        private String name;
    }

    private OrganizationalUnit organizationalUnit;

    private int number;
    private String name;
    private double area;
    private int maximumOccupancy;

    private UUID id;
}
