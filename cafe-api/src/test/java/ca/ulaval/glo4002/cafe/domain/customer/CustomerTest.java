package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerTest {
    private static final CustomerId CUSTOMER_ID = new CustomerId("ID");
    private static final String CUSTOMER_NAME = "JOE BLOW";
    private static final String EMPTY_RESERVATION_NAME = "";
    private static final String VALID_RESERVATION_NAME = "Blue man group";
    private static final String SOME_COFFEE_DRINK = "Espresso";
    private static final String FIRST_COFFEE_ORDER = "Dark Roast";
    private static final String SECOND_COFFEE_ORDER = "Macchiato";

    private Customer someCustomer;

    @BeforeEach
    void setUp() {
        someCustomer = new Customer(CUSTOMER_ID, CUSTOMER_NAME, EMPTY_RESERVATION_NAME);
    }

    @Test
    void givenNewCustomer_whenGetOrders_thenReturnEmptyListOfOrders() {
        List<String> ordersName = someCustomer.getOrdersString();

        assertTrue(ordersName.isEmpty());
    }

    @Test
    void givenNewCustomer_whenPutOrders_thenCustomerHasOrder() {
        var expected = List.of(SOME_COFFEE_DRINK);

        someCustomer.putOrders(List.of(new Coffee(SOME_COFFEE_DRINK, null, null)));

        var actual = someCustomer.getOrdersString();
        assertEquals(expected, actual);
    }

    @Test
    void givenNewCustomer_whenCheckOut_thenIsCheckedOut() {
        someCustomer.checkOut();

        assertTrue(someCustomer.isCheckedOut());
    }

    @Test
    void givenCustomerWithAOrder_whenPutOrders_thenCustomerHasOrderWithCorrectSequence() {
        var expectedOrdersString = List.of(FIRST_COFFEE_ORDER, SECOND_COFFEE_ORDER);
        var expectedOrders = List.of(new Coffee(FIRST_COFFEE_ORDER, null, null),
                                     new Coffee(SECOND_COFFEE_ORDER, null, null));
        someCustomer.putOrders(List.of(new Coffee(FIRST_COFFEE_ORDER, null, null)));

        someCustomer.putOrders(List.of(new Coffee(SECOND_COFFEE_ORDER, null, null)));

        var actualOrdersString = someCustomer.getOrdersString();
        var actualOrders = someCustomer.getOrders();
        assertEquals(expectedOrdersString, actualOrdersString);
    }

    @Test
    void givenCustomerWithReservationName_whenCreatingCustomer_thenCustomerIsInReservation() {
        var customerInGroup = new Customer(CUSTOMER_ID, CUSTOMER_NAME, VALID_RESERVATION_NAME);

        assertTrue(customerInGroup.isInReservation());
    }

    @Test
    void givenCustomerWithoutReservationName_whenCreatingCustomer_thenCustomerIsNotInReservation() {
        var customerInGroup = new Customer(CUSTOMER_ID, CUSTOMER_NAME, EMPTY_RESERVATION_NAME);

        assertFalse(customerInGroup.isInReservation());
    }
}
