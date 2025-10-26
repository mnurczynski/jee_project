package pl.edu.pg.eti.kask.rpg.building.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.model.OrganizationalUnitModel;
import pl.edu.pg.eti.kask.rpg.building.service.OrganizationalUnitService;
import pl.edu.pg.eti.kask.rpg.component.ModelFunctionFactory;

import java.util.Optional;
import java.util.UUID;

/**
 * Faces converter for {@link OrganizationalUnitModel}. The managed attribute in {@link @FacesConverter} allows the converter to
 * be the CDI bean. In previous version of JSF converters were always created inside JSF lifecycle and where not managed
 * by container that is injection was not possible. As this bean is not annotated with scope the beans.xml descriptor
 * must be present.
 */
@FacesConverter(forClass = OrganizationalUnitModel.class, managed = true)
public class OrganizationalUnitModelConverter implements Converter<OrganizationalUnitModel> {

    /**
     * Service for organizationalUnits management.
     */
    private final OrganizationalUnitService service;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;


    /**
     * @param service service for organizationalUnits management
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public OrganizationalUnitModelConverter(OrganizationalUnitService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public OrganizationalUnitModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<OrganizationalUnit> organizationalUnit = service.find(UUID.fromString(value));
        return organizationalUnit.map(factory.organizationalUnitToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, OrganizationalUnitModel value) {
        return value == null ? "" : value.getId().toString();
    }

}
