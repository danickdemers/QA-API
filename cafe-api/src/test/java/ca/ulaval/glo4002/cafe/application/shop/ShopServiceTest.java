package ca.ulaval.glo4002.cafe.application.shop;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.application.shop.dtos.ShopDto;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Menu;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.Shop;
import ca.ulaval.glo4002.cafe.domain.shop.ShopFactory;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;
import ca.ulaval.glo4002.cafe.domain.shop.strategy.ReservationType;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada.Province;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Country;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.TaxerFactory;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates.State;
import ca.ulaval.glo4002.cafe.infrastructure.ShopInitializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {
    private static final String A_SHOP_NAME = "Hiss Kaffee";
    private static final int A_NB_OF_SEATS = 4;
    private static final Country A_COUNTRY = Country.CA;
    private static final Province A_PROVINCE = Province.QC;
    private static final State A_STATE = State.TX;
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("abd4bced-4fce-44a0-aa51-czdc27s7a67a");
    private static final GroupTipRate A_GROUP_TIP_RATE = new GroupTipRate(0);

    @Mock
    private Shop shop;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private ShopDtoAssembler shopDtoAssembler;
    @Mock
    private ShopFactory shopFactory;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private TaxerFactory taxerFactory;
    @Mock
    private IngredientInventoryRepository inventoryRepository;
    @Mock
    private ShopInitializer shopInitializer;

    private ShopService shopService;

    @BeforeEach
    void setUp() {
        shopService = new ShopService(shopRepository, shopDtoAssembler, shopFactory, taxerFactory,
                                      customerRepository, inventoryRepository, shopInitializer);
    }

    @Test
    void givenShopExists_whenGetShop_thenGetShopFromRepository() {
        givenShopExists();

        shopService.getShop();

        verify(shopRepository).getShop();
    }

    @Test
    void givenShopExists_whenGetShop_ShopDtoIsReturned() {
        var initialShop = new Shop("Coffee Shop", new ArrayList<>());
        var expectedShopDto = new ShopDto("Coffee Shop", new ArrayList<>());

        when(shopRepository.getShop()).thenReturn(initialShop);
        when(shopDtoAssembler.shopToDto(initialShop)).thenReturn(expectedShopDto);
        var actualShopDto = shopService.getShop();

        assertEquals(expectedShopDto, actualShopDto);
    }

    @Test
    void whenConfig_thenDelegateShopCreationToFactory() {
        givenFactoryCanCreateShop();
        when(shopRepository.getShop()).thenReturn(shop);
        when(shopInitializer.createMenu()).thenReturn(new Menu());

        shopService.config(A_SHOP_NAME, A_NB_OF_SEATS, ReservationType.DEFAULT, A_COUNTRY,
                           A_PROVINCE, A_STATE, A_GROUP_TIP_RATE);

        verify(shopFactory).create(A_SHOP_NAME, A_NB_OF_SEATS);
    }

    @Test
    void whenConfig_thenSetReservationStrategy() {
        givenFactoryCanCreateShop();

        shopService.config(A_SHOP_NAME, A_NB_OF_SEATS, ReservationType.DEFAULT, A_COUNTRY,
                           A_PROVINCE, A_STATE, A_GROUP_TIP_RATE);

        verify(shop).setReservationStrategy(ReservationType.DEFAULT);
    }

    @Test
    void whenConfig_thenSaveShop() {
        givenFactoryCanCreateShop();

        shopService.config(A_SHOP_NAME, A_NB_OF_SEATS, ReservationType.DEFAULT, A_COUNTRY,
                           A_PROVINCE, A_STATE, A_GROUP_TIP_RATE);

        verify(shopFactory).create(A_SHOP_NAME, A_NB_OF_SEATS);
        verify(shopRepository, times(2)).save(shop);
    }

    @Test
    void whenConfig_thenResetShopAndCustomers() {
        givenFactoryCanCreateShop();
        shopService = spy(shopService);

        shopService.config(A_SHOP_NAME, A_NB_OF_SEATS, ReservationType.DEFAULT, A_COUNTRY,
                           A_PROVINCE, A_STATE, A_GROUP_TIP_RATE);

        verify(shopService).resetShop();
        verify(shopService).resetCustomers();
    }

    @Test
    void whenConfig_thenSetTaxer() {
        givenFactoryCanCreateShop();
        Taxer taxer = mock(Taxer.class);
        when(taxerFactory.create(A_COUNTRY, A_PROVINCE, A_STATE)).thenReturn(taxer);

        shopService.config(A_SHOP_NAME, A_NB_OF_SEATS, ReservationType.DEFAULT, A_COUNTRY,
                           A_PROVINCE, A_STATE, A_GROUP_TIP_RATE);

        verify(taxerFactory).create(A_COUNTRY, A_PROVINCE, A_STATE);
        verify(shop).setConfig(taxer, A_GROUP_TIP_RATE);
    }

    @Test
    void whenFreeSeat_FreeSeatInShop() {
        shop.freeSeat(A_CUSTOMER_ID);

        verify(shop).freeSeat(A_CUSTOMER_ID);
    }

    @Test
    void whenResetShop() {
        var menu = new Menu();
        when(shopRepository.getShop()).thenReturn(shop);
        when(shopInitializer.createMenu()).thenReturn(menu);

        shopService.resetShop();

        verify(shopRepository).reset();
    }

    private void givenShopExists() {
        given(shopRepository.getShop()).willReturn(shop);
    }

    private void givenFactoryCanCreateShop() {
        given(shopFactory.create(A_SHOP_NAME, A_NB_OF_SEATS)).willReturn(shop);
        when(shopRepository.getShop()).thenReturn(shop);
        when(shopInitializer.createMenu()).thenReturn(new Menu());
    }
}
