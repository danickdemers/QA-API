package ca.ulaval.glo4002.cafe.api.customer.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckOutRequestTest {
    private static final String CUSTOMER_ID = "abd4bced-4fce-44a0-aa51-czdc27s7a67a";

    @Test
    void givenEmptyParameters_whenCheckOutRequest_thenAllParametersAreNotDefined() {
        CheckOutRequest checkOutRequest = new CheckOutRequest(null);

        assertFalse(checkOutRequest.allParametersAreDefined());
    }

    @Test
    void givenCustomerId_whenCheckOutRequest_thenAllParametersAreDefined() {
        CheckOutRequest checkOutRequest = new CheckOutRequest(CUSTOMER_ID);

        assertTrue(checkOutRequest.allParametersAreDefined());
    }
}
