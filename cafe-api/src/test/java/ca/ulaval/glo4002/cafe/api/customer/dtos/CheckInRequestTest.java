package ca.ulaval.glo4002.cafe.api.customer.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckInRequestTest {
    private static final String CUSTOMER_ID = "aaaaa-4fce-44a0-aa41-c41c5777e679";
    private static final String CUSTOMER_NAME = "Keanu Reeves";

    @Test
    void givenEmptyParameters_whenCheckInRequest_thenAllParametersAreNotDefined() {
        CheckInRequest checkInRequest = new CheckInRequest(null, null, null);

        assertFalse(checkInRequest.allParametersAreDefined());
    }

    @Test
    void givenParametersWithoutGroup_whenCheckInRequest_thenAllParametersAreDefined() {
        CheckInRequest checkInRequest = new CheckInRequest(CUSTOMER_ID, CUSTOMER_NAME, null);

        assertTrue(checkInRequest.allParametersAreDefined());
    }

    @Test
    void givenFullParameters_whenCheckInRequest_thenAllParametersAreDefined() {
        CheckInRequest checkInRequest = new CheckInRequest(CUSTOMER_ID, CUSTOMER_NAME, null);

        assertTrue(checkInRequest.allParametersAreDefined());
    }

    @Test
    void givenParametersWithoutCustomerName_whenCheckInRequest_thenAllParametersAreNotDefined() {
        CheckInRequest checkInRequest = new CheckInRequest(CUSTOMER_ID, null, null);

        assertFalse(checkInRequest.allParametersAreDefined());
    }

    @Test
    void givenParametersWithoutCustomerId_whenCheckInRequest_thenAllParametersAreNotDefined() {
        CheckInRequest checkInRequest = new CheckInRequest(null, CUSTOMER_NAME, null);

        assertFalse(checkInRequest.allParametersAreDefined());
    }
}
