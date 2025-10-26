package pl.edu.pg.eti.kask.rpg.building.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingEditModel;
import pl.edu.pg.eti.kask.rpg.building.service.BuildingService;
import pl.edu.pg.eti.kask.rpg.component.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering single building edit form.
 */
@ViewScoped
@Named
public class BuildingEdit implements Serializable {

    /**
     * Service for managing buildings.
     */
    private final BuildingService service;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * Building id.
     */
    @Setter
    @Getter
    private UUID id;

    /**
     * Building exposed to the view.
     */
    @Getter
    private BuildingEditModel building;


    /**
     * @param service service for managing buildings
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public BuildingEdit(BuildingService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Building> building = service.find(id);
        if (building.isPresent()) {
            this.building = factory.buildingToEditModel().apply(building.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Building not found");
        }
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        service.update(factory.updateBuilding().apply(service.find(id).orElseThrow(), building));
        return "/building/building_list.xhtml?faces-redirect=true";
    }

}
