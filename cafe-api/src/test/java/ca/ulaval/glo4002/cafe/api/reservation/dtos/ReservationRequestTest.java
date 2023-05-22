package ca.ulaval.glo4002.cafe.api.reservation.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReservationRequestTest {
    private static final String RESERVATION_NAME = "RESERVATION";
    private static final int GROUP_SIZE_OF_FIVE = 5;
    @Test
    void givenEmptyParameters_whenReservationRequest_thenAllParametersAreNotDefined() {
        ReservationRequest reservationRequest = new ReservationRequest();

        assertFalse(reservationRequest.allParametersAreDefined());
    }

    @Test
    void givenFullParameters_whenReservationRequest_thenAllParametersAreDefined() {
        ReservationRequest reservationRequest = new ReservationRequest(RESERVATION_NAME, GROUP_SIZE_OF_FIVE);

        assertTrue(reservationRequest.allParametersAreDefined());
    }

    @Test
    void givenParametersWithoutGroupName_whenReservationRequest_thenAllParametersAreNotDefined() {
        ReservationRequest reservationRequest = new ReservationRequest(null, GROUP_SIZE_OF_FIVE);

        assertFalse(reservationRequest.allParametersAreDefined());
    }

    @Test
    void givenParametersWithoutGroupSize_whenReservationRequest_thenAllParametersAreNotDefined() {
        ReservationRequest reservationRequest = new ReservationRequest(RESERVATION_NAME, null);

        assertFalse(reservationRequest.allParametersAreDefined());
    }
}
