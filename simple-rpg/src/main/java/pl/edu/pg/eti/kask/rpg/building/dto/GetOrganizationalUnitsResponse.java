package pl.edu.pg.eti.kask.rpg.building.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetOrganizationalUnitsResponse {

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

    private List<OrganizationalUnit>organizationalUnitList;
}
