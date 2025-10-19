package pl.edu.pg.eti.kask.rpg.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextListener;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.building.service.BuildingService;
import pl.edu.pg.eti.kask.rpg.building.service.OrganizationalUnitService;
import pl.edu.pg.eti.kask.rpg.user.entity.Type;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.service.UserService;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Listener started automatically on servlet context initialized. Fetches instance of the datasource from the servlet
 * context and fills it with default content. Normally this class would fetch database datasource and init data only in
 * cases of empty database. When using persistence storage application instance should be initialized only during first
 * run in order to init database with starting data. Good place to create first default admin user.
 */
@ApplicationScoped
public class InitializedData implements ServletContextListener {

    /**
     * Character service.
     */
    private final BuildingService buildingService;

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Profession service.
     */
    private final OrganizationalUnitService organizationalUnitService;

    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(BuildingService buildingService, OrganizationalUnitService organizationalUnitService, UserService userService, RequestContextController requestContextController) {
        this.buildingService = buildingService;
        this.organizationalUnitService = organizationalUnitService;
        this.userService = userService;
        this.requestContextController = requestContextController;
    }


    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }


    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @SneakyThrows
    private void init() {
        requestContextController.activate();
        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("admin")
                .hiringDate(LocalDate.of(1990, 10, 21))
                .type(Type.MANAGER)
                .hashedPassword("adminadmin")
                .build();

        User kevin = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .login("Kevin")
                .hiringDate(LocalDate.of(2010, 9, 3))
                .type(Type.BUILDING_ADMINISTRATOR)
                .hashedPassword("useruser")
                .build();

        User api_user = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .login("campus map service")
                .hiringDate(LocalDate.of(1990, 10, 21))
                .type(Type.READ_ONLY_USER)
                .hashedPassword("some_api_key")
                .build();

        User api_user_2 = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4112"))
                .login("CCTV monitoring service")
                .hiringDate(LocalDate.of(1990, 10, 21))
                .type(Type.READ_ONLY_USER)
                .hashedPassword("some_other_api_key")
                .build();

        userService.create(admin);
        userService.create(kevin);
        userService.create(api_user);
        userService.create(api_user_2);

        OrganizationalUnit kwestura = OrganizationalUnit.builder()
                .id(UUID.fromString("f5875513-bf7b-4ae1-b8a5-5b70a1b90e76"))
                .name("Kwestura")
                .required_occupancy(40)
                .type(pl.edu.pg.eti.kask.rpg.building.entity.Type.ADMINISTRATIVE_DEPARTMENT)
                .build();

        OrganizationalUnit nano = OrganizationalUnit.builder()
                .id(UUID.fromString("5d1da2ae-6a14-4b6d-8b4f-d117867118d4"))
                .name("Centrum Nanotechnologii")
                .required_occupancy(700)
                .type(pl.edu.pg.eti.kask.rpg.building.entity.Type.CENTER)
                .build();

        OrganizationalUnit wilis = OrganizationalUnit.builder()
                .id(UUID.fromString("2d9b1e8c-67c5-4188-a911-5f064a63d8cd"))
                .name("Wydział Inżynierii Lądowej i Środowiska ")
                .required_occupancy(4000)
                .type(pl.edu.pg.eti.kask.rpg.building.entity.Type.FACULTY)
                .build();

        OrganizationalUnit oio = OrganizationalUnit.builder()
                .id(UUID.randomUUID())
                .name("Instytut Okrętownictwa i Oceanotechniki ")
                .required_occupancy(2000)
                .type(pl.edu.pg.eti.kask.rpg.building.entity.Type.FACULTY)
                .build();

        organizationalUnitService.create(kwestura);
        organizationalUnitService.create(nano);
        organizationalUnitService.create(wilis);
        organizationalUnitService.create(oio);

        Building gmach_b = Building.builder()
                .id(UUID.fromString("525d3e7b-bb1f-4c13-bf17-926d1a12e4c0"))
                .number(10)
                .name("Gmach B")
                .area(3000)
                .maximumOccupancy(300)
                .buildingAdministrator(kevin)
                .occupant(kwestura)
                .build();

        Building nano_a = Building.builder()
                .id(UUID.fromString("cc0b0577-bb6f-45b7-81d6-3db88e6ac19f"))
                .name("Centrum Nanotechnologii A")
                .number(4)
                .area(1300)
                .maximumOccupancy(600)
                .buildingAdministrator(kevin)
                .occupant(nano)
                .build();

        Building oio_a = Building.builder()
                .id(UUID.fromString("f08ef7e3-7f2a-4378-b1fb-2922d730c70d"))
                .name("Budynek Instytutu Oceanotechniki i Okrętownictwa")
                .number(30)
                .area(3200)
                .maximumOccupancy(2000)
                .buildingAdministrator(admin)
                .occupant(oio)
                .build();

        Building zelbet = Building.builder()
                .id(UUID.fromString("ff327e8a-77c0-4f9b-90a2-89e16895d1e1"))
                .name("Żelbet")
                .number(21)
                .area(2700)
                .maximumOccupancy(1400)
                .buildingAdministrator(kevin)
                .occupant(wilis)
                .build();

        buildingService.create(zelbet);
        buildingService.create(oio_a);
        buildingService.create(nano_a);
        buildingService.create(gmach_b);

        requestContextController.deactivate();
    }

    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}
