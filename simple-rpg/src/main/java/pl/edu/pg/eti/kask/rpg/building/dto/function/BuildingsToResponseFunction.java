package pl.edu.pg.eti.kask.rpg.building.dto.function;

import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingsResponse;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;

import java.util.List;
import java.util.function.Function;

public class BuildingsToResponseFunction implements Function<List<Building>, GetBuildingsResponse> {
    public GetBuildingsResponse apply(List<Building> entities) {
        return GetBuildingsResponse.builder().buildingList(entities.stream().map(building -> GetBuildingsResponse.Building.builder().id(building.getId()).name(building.getName()).build()).toList()).build();
    }
}
