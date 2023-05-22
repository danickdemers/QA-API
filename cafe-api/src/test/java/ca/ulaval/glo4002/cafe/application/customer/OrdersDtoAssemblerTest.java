package ca.ulaval.glo4002.cafe.application.customer;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.customer.dtos.OrdersDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrdersDtoAssemblerTest {
    private static final String SOME_COFFEE_DRINK = "Latte";
    private OrdersDtoAssembler ordersDtoAssembler;

    @BeforeEach
    void setUp() {
        ordersDtoAssembler = new OrdersDtoAssembler();
    }

    @Test
    public void givenOrders_whenMappingToResponseOrders_thenShouldHaveSamePropertyValues() {
        List<String> expectedOrders = List.of(SOME_COFFEE_DRINK, SOME_COFFEE_DRINK);

        OrdersDto orderResponse = ordersDtoAssembler.ordersToOrdersDto(expectedOrders);

        assertEquals(expectedOrders, orderResponse.orders());
    }
}
