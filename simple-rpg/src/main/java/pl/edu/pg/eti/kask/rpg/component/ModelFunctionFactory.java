package pl.edu.pg.eti.kask.rpg.component;

import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingEditModel;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingModel;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingsModel;
import pl.edu.pg.eti.kask.rpg.building.model.OrganizationalUnitModel;
import pl.edu.pg.eti.kask.rpg.building.model.function.*;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
@ApplicationScoped
public class ModelFunctionFactory {

    /**
     * Returns a function to convert a single {@link Building} to {@link BuildingModel}.
     *
     * @return new instance
     */
    public BuildingToModelFunction buildingToModel() {
        return new BuildingToModelFunction();
    }

    /**
     * Returns a function to convert a list of {@link Building} to {@link BuildingsModel}.
     *
     * @return new instance
     */
    public BuildingsToModelFunction buildingsToModel() {
        return new BuildingsToModelFunction();
    }

    /**
     * Returns a function to convert a single {@link Building} to {@link BuildingEditModel}.
     *
     * @return new instance
     */
    public BuildingToEditModelFunction buildingToEditModel() {
        return new BuildingToEditModelFunction();
    }

    /**
     * Returns a function to convert a single {@link BuildingModel} to {@link Building}.
     *
     * @return new instance
     */
    public ModelToBuildingFunction modelToBuilding() {
        return new ModelToBuildingFunction();
    }


    /**
     * Returns a function to convert a single {@link OrganizationalUnit} to {@link OrganizationalUnitModel}.
     *
     * @return new instance
     */
    public OrganizationalUnitToModelFunction organizationalUnitToModel() {
        return new OrganizationalUnitToModelFunction();
    }

    public OrganizationalUnitsToModelFunction organizationalUnitsToModel() {
        return new OrganizationalUnitsToModelFunction();
    }


    /**
     * Returns a function to update a {@link Building}.
     *
     * @return UpdateBuildingFunction instance
     */
    public UpdateBuildingWithModelFunction updateBuilding() {
        return new UpdateBuildingWithModelFunction();
    }

}
