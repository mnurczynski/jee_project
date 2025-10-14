package pl.edu.pg.eti.kask.rpg.configuration.listener;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.rpg.building.repository.api.BuildingRepository;
import pl.edu.pg.eti.kask.rpg.building.repository.api.OrganizationalUnitRepository;
import pl.edu.pg.eti.kask.rpg.building.repository.memory.BuildingInMemoryRepository;
import pl.edu.pg.eti.kask.rpg.building.repository.memory.OrganizationalUnitInMemoryRepository;
import pl.edu.pg.eti.kask.rpg.building.service.BuildingService;
import pl.edu.pg.eti.kask.rpg.building.service.OrganizationalUnitService;
import pl.edu.pg.eti.kask.rpg.crypto.component.Pbkdf2PasswordHash;
import pl.edu.pg.eti.kask.rpg.datastore.component.DataStore;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.rpg.user.repository.memory.UserInMemoryRepository;
import pl.edu.pg.eti.kask.rpg.user.service.UserService;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of services (business layer) and
 * puts them in the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateServices implements ServletContextListener {


    @Resource(name = "USER_AVATAR_PATH")
    private String USER_AVATAR_PATH;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");



        UserRepository userRepository = new UserInMemoryRepository(dataSource, USER_AVATAR_PATH);
        OrganizationalUnitRepository organizationalUnitRepository = new OrganizationalUnitInMemoryRepository(dataSource);
        BuildingRepository buildingRepository = new BuildingInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("userService", new UserService(userRepository, new Pbkdf2PasswordHash()));
        event.getServletContext().setAttribute("buildingService", new BuildingService(userRepository, buildingRepository, organizationalUnitRepository));
        event.getServletContext().setAttribute("organizationalUnitService", new OrganizationalUnitService(userRepository, buildingRepository, organizationalUnitRepository));
    }

}
