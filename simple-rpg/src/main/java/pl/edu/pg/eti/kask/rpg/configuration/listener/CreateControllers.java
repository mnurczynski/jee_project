package pl.edu.pg.eti.kask.rpg.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.rpg.building.controller.simple.BuildingSimpleController;
import pl.edu.pg.eti.kask.rpg.building.controller.simple.OrganizationalUnitSimpleController;
import pl.edu.pg.eti.kask.rpg.building.service.BuildingService;
import pl.edu.pg.eti.kask.rpg.building.service.OrganizationalUnitService;
import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.user.controller.simple.UserSimpleController;
import pl.edu.pg.eti.kask.rpg.user.service.UserService;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of controllers and puts them in
 * the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        BuildingService characterService = (BuildingService) event.getServletContext().getAttribute("buildingService");
        OrganizationalUnitService professionService = (OrganizationalUnitService) event.getServletContext().getAttribute("organizationalUnitService");
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");

        event.getServletContext().setAttribute("buildingController", new BuildingSimpleController(
                characterService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("organizationalUnitController", new OrganizationalUnitSimpleController(
                professionService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("userController", new UserSimpleController(
                userService,
                new DtoFunctionFactory()
        ));
    }
}
