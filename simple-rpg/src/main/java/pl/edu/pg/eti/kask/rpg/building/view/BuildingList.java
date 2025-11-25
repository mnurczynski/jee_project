package pl.edu.pg.eti.kask.rpg.building.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingsModel;
import pl.edu.pg.eti.kask.rpg.building.service.BuildingService;
import pl.edu.pg.eti.kask.rpg.component.ModelFunctionFactory;

/**
 * View bean for rendering list of buildings.
 */
@RequestScoped
@Named
public class BuildingList {

    /**
     * Service for managing buildings.
     */
    private final BuildingService service;

    /**
     * Buildings list exposed to the view.
     */
    private BuildingsModel buildings;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * @param service building service
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public BuildingList(BuildingService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all buildings
     */
    public BuildingsModel getBuildings() {
        if (buildings == null) {
            buildings = factory.buildingsToModel().apply(service.findAllForCallerPrincipal().get());
        }
        return buildings;
    }

    /**
     * Action for clicking delete action.
     *
     * @param building building to be removed
     * @return navigation case to list_buildings
     */
    public String deleteAction(BuildingsModel.Building building) {
        service.delete(building.getId());
        return "building_list?faces-redirect=true";
    }

}
