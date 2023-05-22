package ca.ulaval.glo4002.cafe.application.customer;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.application.customer.dtos.OrdersDto;
import ca.ulaval.glo4002.cafe.application.inventory.IngredientInventoryService;
import ca.ulaval.glo4002.cafe.domain.customer.Coffee;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Menu;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.DuplicateCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.shop.CustomerInformation;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.Shop;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    private static final CustomerId SOME_CUSTOMER_ID = new CustomerId("e83892e39e");
    private static final String SOME_CUSTOMER_NAME = "Keanu Reeves";
    private static final String SOME_RESERVATION_NAME = "Reservation";

    private static final String SOME_VALID_ORDER = "Espresso";
    private static final String SOME_INVALID_ORDER = "Mexico";
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private CustomerDtoAssembler customerDtoAssembler;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private Shop shop;
    @Mock
    private Customer customer;
    @Mock
    private CustomerBillAssembler customerBillAssembler;
    @Mock
    private IngredientInventoryService ingredientInventoryService;
    @Mock
    private CustomerFactory customerFactory;
    @Mock
    private CustomerInformation customerInformation;
    @Mock
    private Menu menu;
    @Mock
    private Coffee coffee;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        OrdersDtoAssembler ordersDtoAssembler = new OrdersDtoAssembler();
        customerService = new CustomerService(customerRepository, shopRepository, customerDtoAssembler,
                                              customerBillAssembler, ingredientInventoryService, customerFactory,
                                              ordersDtoAssembler);
        shop.setMenu(menu);
    }

    @Test
    void givenShopRepository_whenCheckInCustomer_thenGetShop() {
        givenShopRepository();
        given(customerFactory.create(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, null)).willReturn(
                new Customer(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, null));

        customerService.checkInCustomer(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, null);

        verify(shopRepository).getShop();
    }

    @Test
    void givenShopRepositoryAndCustomerWithReservation_whenCheckInCustomer_thenUpdateShopRepository() {
        givenShopRepository();
        given(customerFactory.create(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, SOME_RESERVATION_NAME)).willReturn(
                new Customer(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, SOME_RESERVATION_NAME));

        customerService.checkInCustomer(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, SOME_RESERVATION_NAME);

        verify(shop).assignCustomerToAReservationSeat(SOME_CUSTOMER_ID, SOME_RESERVATION_NAME);
        verify(shopRepository).save(shop);
    }

    @Test
    void givenShopRepositoryAndCustomerWithoutReservation_whenCheckInCustomer_thenUpdateShopRepository() {
        givenShopRepository();
        given(customerFactory.create(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, null)).willReturn(
                new Customer(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, null));

        customerService.checkInCustomer(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, null);

        verify(shop).assignCustomerToAnAvailableSeat(SOME_CUSTOMER_ID);
        verify(shopRepository).save(shop);
    }

    @Test
    void givenInvalidCustomerWithDuplicateId_whenCheckInCustomer_thenThrowsDuplicateCustomerIdException() {
        givenShopRepository();
        given(customerRepository.exists(SOME_CUSTOMER_ID)).willReturn(true);
        given(customerFactory.create(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, SOME_RESERVATION_NAME)).willReturn(
                new Customer(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, SOME_RESERVATION_NAME));

        assertThrows(DuplicateCustomerIdException.class,
                     () -> customerService.checkInCustomer(SOME_CUSTOMER_ID, SOME_CUSTOMER_NAME, SOME_RESERVATION_NAME)
        );
    }

    @Test
    void givenValidCustomerAndListOfOrders_whenPutOrders_thenPutOrdersInCustomer() {
        var orders = List.of(SOME_VALID_ORDER, SOME_VALID_ORDER, SOME_VALID_ORDER, SOME_VALID_ORDER);
        given(customerRepository.findById(SOME_CUSTOMER_ID)).willReturn(customer);
        given(shopRepository.getShop()).willReturn(shop);
        given(shop.getMenu()).willReturn(menu);
        given(menu.getCoffee(any())).willReturn(coffee);

        customerService.putOrders(SOME_CUSTOMER_ID, orders);

        verify(customer).putOrders(any());
    }

    @Test
    void givenCustomer_whenGetOrders_thenReturnOrdersDtoOfCustomer() {
        List<String> expectedOrders = List.of(SOME_VALID_ORDER, SOME_VALID_ORDER);
        OrdersDto expectedOrdersDto = new OrdersDto(expectedOrders);
        when(customer.getOrdersString()).thenReturn(expectedOrders);
        when(customerRepository.findById(SOME_CUSTOMER_ID)).thenReturn(customer);

        OrdersDto actualOrdersDto = customerService.getOrders(SOME_CUSTOMER_ID);

        assertEquals(expectedOrdersDto, actualOrdersDto);
    }

    @Test
    void givenValidCustomer_whenCheckOutCustomer_thenUpdateCheckedOutCustomer() {
        given(customerRepository.findById(SOME_CUSTOMER_ID)).willReturn(customer);

        customerService.checkOutCustomer(SOME_CUSTOMER_ID);

        verify(customer).checkOut();
        verify(customerRepository).update(customer);
    }

    @Test
    void givenShopWithValidCustomer_whenGetCustomer_thenReturnsCustomerInformation() {
        givenShopRepository();
        given(shop.retrieveCustomerSeatInformation(SOME_CUSTOMER_ID)).willReturn(customerInformation);
        given(customerRepository.findById(SOME_CUSTOMER_ID)).willReturn(customer);

        customerService.getCustomer(SOME_CUSTOMER_ID);

        verify(shop).retrieveCustomerSeatInformation(SOME_CUSTOMER_ID);
        verify(customerInformation).getReservationName();
    }

    @Test
    void givenValidCustomer_whenGetBill_thenBillCreated() {
        Customer customer = mock(Customer.class);
        Shop shop = mock(Shop.class);
        Taxer taxer = mock(Taxer.class);
        GroupTipRate groupTipRate = new GroupTipRate(0);
        when(customerRepository.findById(SOME_CUSTOMER_ID)).thenReturn(customer);
        when(shopRepository.getShop()).thenReturn(shop);
        when(shop.getTaxer()).thenReturn(taxer);
        when(shop.getGroupTipRate()).thenReturn(groupTipRate);

        customerService.getBill(SOME_CUSTOMER_ID);

        verify(customer).createBill(taxer, groupTipRate);
    }

    @Test
    public void givenValidCustomerIdAndListOfOrders_whenPutOrders_thenRemoveIngredientsFromInventory() {
        var orders = List.of(SOME_VALID_ORDER, SOME_VALID_ORDER, SOME_VALID_ORDER, SOME_VALID_ORDER);
        given(customerRepository.findById(SOME_CUSTOMER_ID)).willReturn(customer);
        given(shopRepository.getShop()).willReturn(shop);
        given(shop.getMenu()).willReturn(menu);
        given(menu.getCoffee(any())).willReturn(coffee);

        customerService.putOrders(SOME_CUSTOMER_ID, orders);

        verify(ingredientInventoryService).removeInventoryForOrder(any());
    }

    private void givenShopRepository() {
        given(shopRepository.getShop()).willReturn(shop);
    }
}
