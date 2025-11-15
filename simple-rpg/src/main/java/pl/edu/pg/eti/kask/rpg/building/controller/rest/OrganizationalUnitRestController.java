package pl.edu.pg.eti.kask.rpg.building.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import pl.edu.pg.eti.kask.rpg.building.controller.api.OrganizationalUnitController;
import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitsResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.PatchOrganizationalUnitRequest;
import pl.edu.pg.eti.kask.rpg.building.dto.PutOrganizationalUnitRequest;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.service.OrganizationalUnitService;
import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.user.entity.Type;

import java.util.Optional;
import java.util.UUID;

@Path("")
@RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
public class OrganizationalUnitRestController implements OrganizationalUnitController {

    private final OrganizationalUnitService service;
    private final DtoFunctionFactory factory;

    @Inject
    public OrganizationalUnitRestController(OrganizationalUnitService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public GetOrganizationalUnitsResponse getOrganizationalUnits() {
        return service.findAll().map(factory.organizationalUnitsToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed({Type.BUILDING_ADMINISTRATOR, Type.MANAGER, Type.READ_ONLY_USER})
    public GetOrganizationalUnitResponse getOrganizationalUnit(UUID uuid) {
        return service.find(uuid).map(factory.organizationalUnitToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed(Type.MANAGER)
    public void deleteOrganizationalUnit(UUID uuid) {
        service.delete(uuid);
    }

    @Override
    @RolesAllowed(Type.MANAGER)
    public void putOrganizationalUnit(UUID uuid, PutOrganizationalUnitRequest organizationalUnit) {
        deleteOrganizationalUnit(uuid);
        service.create(factory.requestToOrganizationalUnit().apply(uuid, organizationalUnit));
    }

    @Override
    @RolesAllowed(Type.MANAGER)
    public void patchOrganizationalUnit(UUID uuid, PatchOrganizationalUnitRequest request) {
        Optional<OrganizationalUnit> organizationalUnit = service.find(uuid);
        if(organizationalUnit.isPresent())
        {
            service.update(factory.updateOrganizationalUnit().apply(organizationalUnit.get(), request));
            return;
        }
        throw new NotFoundException();
    }
}
