package ca.ulaval.glo4002.cafe.application.shop;

import ca.ulaval.glo4002.cafe.application.shop.dtos.ShopDto;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.ShopFactory;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;
import ca.ulaval.glo4002.cafe.domain.shop.strategy.ReservationType;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada.Province;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Country;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.TaxerFactory;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates.State;
import ca.ulaval.glo4002.cafe.infrastructure.ShopInitializer;

public class ShopService {
    private final ShopRepository shopRepository;
    private final ShopDtoAssembler shopDtoAssembler;
    private final ShopFactory shopFactory;
    private final TaxerFactory taxerFactory;
    private final CustomerRepository customerRepository;
    private final ShopInitializer shopInitializer;

    private final IngredientInventoryRepository inventoryRepository;

    public ShopService(ShopRepository shopRepository,
                       ShopDtoAssembler shopDtoAssembler, ShopFactory shopFactory,
                       TaxerFactory taxerFactory, CustomerRepository customerRepository,
                       IngredientInventoryRepository inventoryRepository,
                       ShopInitializer shopInitializer) {
        this.shopRepository = shopRepository;
        this.shopDtoAssembler = shopDtoAssembler;
        this.shopFactory = shopFactory;
        this.taxerFactory = taxerFactory;
        this.customerRepository = customerRepository;
        this.inventoryRepository = inventoryRepository;
        this.shopInitializer = shopInitializer;
    }

    public void config(String name,
                       int cubeSize,
                       ReservationType reservationType,
                       Country country,
                       Province province,
                       State state,
                       GroupTipRate groupTipRate) {
        var shop = shopFactory.create(name, cubeSize);
        shop.setReservationStrategy(reservationType);
        var taxer = taxerFactory.create(country, province, state);
        shop.setConfig(taxer, groupTipRate);
        shopRepository.save(shop);
        resetShop();
        resetCustomers();
        resetInventory();
    }

    public ShopDto getShop() {
        var shop = shopRepository.getShop();
        return shopDtoAssembler.shopToDto(shop);
    }

    public void resetShop() {
        shopRepository.reset();
        var shop = shopRepository.getShop();
        shop.setMenu(shopInitializer.createMenu());
        shopRepository.save(shop);
    }

    public void resetCustomers() {
        customerRepository.reset();
    }

    public void resetInventory() {
        inventoryRepository.reset();
        shopInitializer.createIngredient();
    }

    public void freeSeat(CustomerId customerId) {
        var shop = shopRepository.getShop();
        shop.freeSeat(customerId);
        shopRepository.save(shop);
    }
}
