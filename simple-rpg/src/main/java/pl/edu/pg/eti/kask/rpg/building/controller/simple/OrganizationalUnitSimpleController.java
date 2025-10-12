package pl.edu.pg.eti.kask.rpg.building.controller.simple;

import pl.edu.pg.eti.kask.rpg.building.controller.api.OrganizationalUnitController;
import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitsResponse;

import pl.edu.pg.eti.kask.rpg.building.service.OrganizationalUnitService;
import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.NotFoundException;

public class OrganizationalUnitSimpleController implements OrganizationalUnitController {

    private final OrganizationalUnitService service;
    private final DtoFunctionFactory factory;

    public OrganizationalUnitSimpleController(OrganizationalUnitService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetOrganizationalUnitsResponse getOrganizationalUnits() {
        return service.findAll().map(factory.organizationalUnitsToResponse()).orElseThrow(NotFoundException::new);
    }
}
