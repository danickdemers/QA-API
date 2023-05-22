package ca.ulaval.glo4002.cafe.api.shop.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigRequestTest {
    private static final String STRATEGY_RESERVATION_NAME = "Strategical section 51";
    private static final String RESERVATION_NAME = "Section Z3";
    private static final int RESERVATION_SIZE_OF_5 = 5;
    private static final String A_COUNTRY = "CA";
    private static final String A_PROVINCE = "QC";
    private static final String A_STATE = "TX";
    private static final int A_GROUP_TIP_RATE = 0;

    @Test
    void givenEmptyParameters_whenCheckInRequest_thenAllParametersAreNotDefined() {
        ConfigRequest configRequest = new ConfigRequest();

        assertFalse(configRequest.allParametersAreDefined());
    }

    @Test
    void givenFullParameters_whenCheckInRequest_thenAllParametersAreDefined() {
        ConfigRequest configRequest = new ConfigRequest(STRATEGY_RESERVATION_NAME, RESERVATION_NAME,
                                                        RESERVATION_SIZE_OF_5, A_COUNTRY, A_PROVINCE, A_STATE,
                                                        A_GROUP_TIP_RATE);

        assertTrue(configRequest.allParametersAreDefined());
    }

    @Test
    void givenParametersWithoutReservationMethod_whenCheckInRequest_thenAllParametersAreNotDefined() {
        ConfigRequest configRequest = new ConfigRequest(null, RESERVATION_NAME,
                                                        RESERVATION_SIZE_OF_5, A_COUNTRY, A_PROVINCE, A_STATE,
                                                        A_GROUP_TIP_RATE);

        assertFalse(configRequest.allParametersAreDefined());
    }
}
