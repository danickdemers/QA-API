package ca.ulaval.glo4002.cafe;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.cafe.api.customer.CustomerController;
import ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers.DuplicateCustomerIdExceptionMapper;
import ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers.InsufficientIngredientsExceptionMapper;
import ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers.InvalidCustomerIdExceptionMapper;
import ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers.InvalidMenuOrderExceptionMapper;
import ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers.NoBillExceptionMapper;
import ca.ulaval.glo4002.cafe.api.heartbeat.HeartbeatController;
import ca.ulaval.glo4002.cafe.api.inventory.InventoryController;
import ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers.AlreadyPresentIngredientInventoryExceptionMapper;
import ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers.IncompatibleUnitsOfMeasureExceptionMapper;
import ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers.InvalidIngredientExceptionMapper;
import ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers.InvalidInventoryExceptionMapper;
import ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers.InvalidInventoryRequestExceptionMapper;
import ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers.NonExistentIngredientInventoryExceptionMapper;
import ca.ulaval.glo4002.cafe.api.menu.MenuController;
import ca.ulaval.glo4002.cafe.api.reservation.ReservationController;
import ca.ulaval.glo4002.cafe.api.reservation.exceptions.mappers.DuplicateGroupNameExceptionMapper;
import ca.ulaval.glo4002.cafe.api.reservation.exceptions.mappers.InvalidGroupSizeExceptionMapper;
import ca.ulaval.glo4002.cafe.api.reservation.exceptions.mappers.NoGroupSeatsExceptionMapper;
import ca.ulaval.glo4002.cafe.api.reservation.exceptions.mappers.NoReservationFoundExceptionMapper;
import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.CatchallExceptionMapper;
import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.MissingParameterExceptionMapper;
import ca.ulaval.glo4002.cafe.api.shop.ShopController;
import ca.ulaval.glo4002.cafe.api.shop.exceptions.mappers.InsufficientSeatsExceptionMapper;
import ca.ulaval.glo4002.cafe.api.shop.exceptions.mappers.InvalidCountryExceptionMapper;
import ca.ulaval.glo4002.cafe.api.shop.exceptions.mappers.InvalidGroupReservationMethodExceptionMapper;
import ca.ulaval.glo4002.cafe.api.shop.exceptions.mappers.InvalidGroupTipRateExceptionMapper;
import ca.ulaval.glo4002.cafe.application.Menu.MenuService;
import ca.ulaval.glo4002.cafe.application.customer.CustomerBillAssembler;
import ca.ulaval.glo4002.cafe.application.customer.CustomerDtoAssembler;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.customer.OrdersDtoAssembler;
import ca.ulaval.glo4002.cafe.application.inventory.IngredientInventoryService;
import ca.ulaval.glo4002.cafe.application.reservations.ReservationDtoAssembler;
import ca.ulaval.glo4002.cafe.application.reservations.ReservationService;
import ca.ulaval.glo4002.cafe.application.shop.CubeDtoAssembler;
import ca.ulaval.glo4002.cafe.application.shop.SeatDtoAssembler;
import ca.ulaval.glo4002.cafe.application.shop.ShopDtoAssembler;
import ca.ulaval.glo4002.cafe.application.shop.ShopService;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.shop.ShopFactory;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.TaxerFactory;
import ca.ulaval.glo4002.cafe.infrastructure.ShopInitializer;
import ca.ulaval.glo4002.cafe.infrastructure.customer.CustomerRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.inventory.IngredientInventoryRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.shop.ShopRepositoryInMemory;

public class CafeServer implements Runnable {
    private static final int PORT = 8181;

    public static void main(String[] args) {
        new CafeServer().run();
    }

    private static ShopService createShopService(ShopRepository shopRepository, ShopFactory shopFactory,
                                                 CustomerRepository customerRepository,
                                                 IngredientInventoryRepository inventoryRepository,
                                                 ShopInitializer shopInitializer) {
        SeatDtoAssembler seatDtoAssembler = new SeatDtoAssembler();
        CubeDtoAssembler cubeDtoAssembler = new CubeDtoAssembler(seatDtoAssembler);
        ShopDtoAssembler shopDtoAssembler = new ShopDtoAssembler(cubeDtoAssembler);
        TaxerFactory taxerFactory = new TaxerFactory();
        return new ShopService(shopRepository, shopDtoAssembler, shopFactory, taxerFactory, customerRepository,
                               inventoryRepository, shopInitializer);
    }

    private static CustomerService createCustomerService(ShopRepository shopRepository,
                                                         CustomerRepository customerRepository,
                                                         IngredientInventoryService ingredientInventoryService) {
        CustomerDtoAssembler customerAssembler = new CustomerDtoAssembler();
        CustomerBillAssembler customerBillAssembler = new CustomerBillAssembler();
        CustomerFactory customerFactory = new CustomerFactory();
        OrdersDtoAssembler ordersDtoAssembler = new OrdersDtoAssembler();

        return new CustomerService(customerRepository, shopRepository, customerAssembler, customerBillAssembler,
                                   ingredientInventoryService, customerFactory, ordersDtoAssembler);
    }

    private static ReservationService createReservationService(ShopRepository shopRepository) {
        ReservationDtoAssembler groupDtoAssembler = new ReservationDtoAssembler();

        return new ReservationService(groupDtoAssembler, shopRepository);
    }

    private static IngredientInventoryService createIngredientInventoryService(IngredientInventoryRepository
                                                                                       ingredientInventoryRepository) {
        return new IngredientInventoryService(ingredientInventoryRepository);
    }

    private static void registerExceptionMappers(ResourceConfig config) {
        config.register(CatchallExceptionMapper.class);
        config.register(AlreadyPresentIngredientInventoryExceptionMapper.class);
        config.register(IncompatibleUnitsOfMeasureExceptionMapper.class);
        config.register(InvalidIngredientExceptionMapper.class);
        config.register(InvalidInventoryExceptionMapper.class);
        config.register(InvalidInventoryRequestExceptionMapper.class);
        config.register(DuplicateCustomerIdExceptionMapper.class);
        config.register(InsufficientSeatsExceptionMapper.class);
        config.register(MissingParameterExceptionMapper.class);
        config.register(InvalidCustomerIdExceptionMapper.class);
        config.register(InvalidGroupSizeExceptionMapper.class);
        config.register(DuplicateGroupNameExceptionMapper.class);
        config.register(NoReservationFoundExceptionMapper.class);
        config.register(NoGroupSeatsExceptionMapper.class);
        config.register(InvalidGroupReservationMethodExceptionMapper.class);
        config.register(InvalidMenuOrderExceptionMapper.class);
        config.register(InvalidCountryExceptionMapper.class);
        config.register(NoBillExceptionMapper.class);
        config.register(NonExistentIngredientInventoryExceptionMapper.class);
        config.register(InsufficientIngredientsExceptionMapper.class);
        config.register(InvalidGroupTipRateExceptionMapper.class);
    }

    public void run() {
        var shopRepository = new ShopRepositoryInMemory();
        var customerRepository = new CustomerRepositoryInMemory();
        var defaultShopFactory = new ShopFactory();
        var ingredientInventoryRepository = new IngredientInventoryRepositoryInMemory();
        var coffeeShopInitializer = new ShopInitializer(shopRepository, defaultShopFactory,
                                                        ingredientInventoryRepository);
        var inventoryService = createIngredientInventoryService(ingredientInventoryRepository);
        var shopService = createShopService(shopRepository, defaultShopFactory, customerRepository,
                                            ingredientInventoryRepository, coffeeShopInitializer);
        var customerService = createCustomerService(shopRepository, customerRepository, inventoryService);
        var reservationService = createReservationService(shopRepository);

        var shopController = createShopController(shopService);
        var customerController = createCustomerController(customerService, shopService);
        var reservationController = createReservationController(reservationService);
        var inventoryController = createInventoryController(inventoryService);

        var menuService = new MenuService(shopRepository);
        var menuController = createMenuController(menuService);

        // Server startup
        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");

        HeartbeatController heartbeatController = new HeartbeatController();

        final AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(shopController).to(ShopController.class);
                bind(customerController).to(CustomerController.class);
                bind(reservationController).to(ReservationController.class);
                bind(inventoryController).to(InventoryController.class);
                bind(heartbeatController).to(HeartbeatController.class);
                bind(menuController).to(MenuController.class);
            }
        };
        final ResourceConfig config = new ResourceConfig();
        registerExceptionMappers(config);
        config.register(binder);
        config.packages("ca.ulaval.glo4002.cafe");

        coffeeShopInitializer.initializeCoffeeShop();

        ServletContainer container = new ServletContainer(config);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (server.isRunning()) {
                server.destroy();
            }
        }
    }

    private ShopController createShopController(ShopService shopService) {
        return new ShopController(shopService);
    }

    private CustomerController createCustomerController(CustomerService customerService, ShopService shopService) {
        return new CustomerController(customerService, shopService);
    }

    private ReservationController createReservationController(
            ReservationService reservationService) {
        return new ReservationController(reservationService);
    }

    private InventoryController createInventoryController(IngredientInventoryService inventoryService) {
        return new InventoryController(inventoryService);
    }

    private MenuController createMenuController(MenuService menuService) {
        return new MenuController(menuService);
    }
}
