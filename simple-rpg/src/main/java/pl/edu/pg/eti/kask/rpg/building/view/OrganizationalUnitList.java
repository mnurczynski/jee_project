package pl.edu.pg.eti.kask.rpg.building.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.rpg.building.model.OrganizationalUnitsModel;
import pl.edu.pg.eti.kask.rpg.building.service.OrganizationalUnitService;
import pl.edu.pg.eti.kask.rpg.component.ModelFunctionFactory;

/**
 * View bean for rendering list of buildings.
 */
@RequestScoped
@Named
public class OrganizationalUnitList {

    /**
     * Service for managing buildings.
     */
    private final OrganizationalUnitService service;

    /**
     * Buildings list exposed to the view.
     */
    private OrganizationalUnitsModel units=null;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * @param service building service
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public OrganizationalUnitList(OrganizationalUnitService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all buildings
     */
    public OrganizationalUnitsModel getUnits() {
        if (units == null) {
            units = factory.organizationalUnitsToModel().apply(service.findAll().get());
        }
        return units;
    }


    public String deleteAction(OrganizationalUnitsModel.OrganizationalUnit unit) {
        service.delete(unit.getId());
        return "organizational_unit_list?faces-redirect=true";
    }

}
