package pl.edu.pg.eti.kask.rpg.building.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingsModel;
import pl.edu.pg.eti.kask.rpg.building.model.OrganizationalUnitModel;
import pl.edu.pg.eti.kask.rpg.building.service.BuildingService;
import pl.edu.pg.eti.kask.rpg.building.service.OrganizationalUnitService;
import pl.edu.pg.eti.kask.rpg.component.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering single building information.
 */
@ViewScoped
@Named
public class OrganizationalUnitView implements Serializable {

    /**
     * Service for managing buildings.
     */
    private final BuildingService buildingService;

    private final OrganizationalUnitService organizationalUnitService;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    private Integer availibleOccupancy = null;

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
    private OrganizationalUnitModel unit;

    @Getter
    private BuildingsModel buildings;

    /**
     * @param buildingService service for managing buildings
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public OrganizationalUnitView(BuildingService buildingService, OrganizationalUnitService organizationalUnitService, ModelFunctionFactory factory) {
        this.buildingService = buildingService;
        this.organizationalUnitService = organizationalUnitService;
        this.factory = factory;
    }

    public int getAvailableOccupancy()
    {
        if(availibleOccupancy != null)
        {
            return availibleOccupancy;
        }
        int sum = 0;
        for(var building : this.buildings.getBuildings())
        {
            sum += buildingService.find(building.getId()).get().getMaximumOccupancy();
        }
        availibleOccupancy = sum;
        return sum;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<OrganizationalUnit> unit = organizationalUnitService.find(id);
        if (unit.isPresent()) {
            this.unit = factory.organizationalUnitToModel().apply(unit.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Unit not found");
        }
        Optional<List<Building>> buildings = buildingService.findAllByOrganizationalUnit(unit.get().getId());
        if (buildings.isPresent()) {
            this.buildings = factory.buildingsToModel().apply(buildings.get());
        } else {
            this.buildings = new BuildingsModel();
        }

    }

    public String deleteBuilding(UUID id)
    {
        buildingService.delete(id);
        return "/organizational_unit/organizational_unit_view?faces-redirect=true&id="+unit.getId().toString();
    }

}
