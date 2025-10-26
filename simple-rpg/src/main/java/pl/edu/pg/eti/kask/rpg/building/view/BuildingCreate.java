package pl.edu.pg.eti.kask.rpg.building.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.model.BuildingCreateModel;
import pl.edu.pg.eti.kask.rpg.building.model.OrganizationalUnitModel;
import pl.edu.pg.eti.kask.rpg.building.service.BuildingService;
import pl.edu.pg.eti.kask.rpg.building.service.OrganizationalUnitService;
import pl.edu.pg.eti.kask.rpg.component.ModelFunctionFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * View bean for rendering single building create form. Creating a building is divided into number of steps where each
 * step is separate JSF view. In order to use single bean, conversation scope is used.
 */
@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class BuildingCreate implements Serializable {

    /**
     * Service for managing buildings.
     */
    private final BuildingService buildingService;

    /**
     * Service for managing organizationalUnits.
     */
    private final OrganizationalUnitService organizationalUnitService;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * Building exposed to the view.
     */
    @Getter
    private BuildingCreateModel building;

    /**
     * Available organizationalUnits.
     */
    @Getter
    private List<OrganizationalUnitModel> organizationalUnits;

    /**
     * Injected conversation.
     */
    private final Conversation conversation;

    /**
     * @param buildingService  service for managing buildings
     * @param organizationalUnitService service for managing organizationalUnits
     * @param factory           factory producing functions for conversion between models and entities
     * @param conversation      injected conversation
     */
    @Inject
    public BuildingCreate(
            BuildingService buildingService,
            OrganizationalUnitService organizationalUnitService,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.buildingService = buildingService;
        this.factory = factory;
        this.organizationalUnitService = organizationalUnitService;
        this.conversation = conversation;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view. @PostConstruct method is called after h:form header is already
     * rendered. Conversation should be started in f:metadata/f:event.
     */
    public void init() {
        if (conversation.isTransient()) {
            List<OrganizationalUnit> organizationalUnitEntities = organizationalUnitService.findAll().get();
            var converter = factory.organizationalUnitToModel();
            organizationalUnits = new ArrayList<OrganizationalUnitModel>();
            for( OrganizationalUnit entity : organizationalUnitEntities)
            {
                organizationalUnits.add(converter.apply(entity));
            }
            building = BuildingCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    /**
     * @return organizationalUnit navigation case
     */
    public String goToOrganizationalUnitAction() {
        return "/building/building_create__organizationalUnit.xhtml?faces-redirect=true";
    }




    /**
     * @return basic information navigation case
     */
    public Object goToBasicAction() {
        return "/building/building_create__basic.xhtml?faces-redirect=true";
    }

    /**
     * Cancels building creation process.
     *
     * @return buildings list navigation case
     */
    public String cancelAction() {
        conversation.end();
        return "/building/building_list.xhtml?faces-redirect=true";
    }

    /**
     * Sets default building properties (leve land health).
     *
     * @return confirmation navigation case
     */
    public String goToConfirmAction() {
        building.setMaximumOccupancy(100);
        return "/building/building_create__confirm.xhtml?faces-redirect=true";
    }

    /**
     * Stores new building and ends conversation.
     *
     * @return buildings list navigation case
     */
    public String saveAction() {
        buildingService.create(factory.modelToBuilding().apply(building));
        conversation.end();
        return "/building/building_list.xhtml?faces-redirect=true";
    }

    /**
     * @return current conversation id
     */
    public String getConversationId() {
        return conversation.getId();
    }

    public String getBuildingPortraitUrl() {
        return "/view/api/v1/buildings/new/portrait?cid=%s".formatted(getConversationId());
    }

}
