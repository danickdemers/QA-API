package ca.ulaval.glo4002.cafe.api.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.api.reservation.dtos.ReservationRequest;
import ca.ulaval.glo4002.cafe.api.shared.exceptions.MissingParameterException;
import ca.ulaval.glo4002.cafe.application.reservations.ReservationService;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
    private static final String SOME_RESERVATION_NAME = "Cool";
    private static final int SOME_RESERVATION_SIZE = 3;
    @Mock
    private ReservationService reservationService;
    private ReservationRequest reservationRequest;
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        reservationController = new ReservationController(reservationService);
    }

    @Test
    void givenMissingParamRequest_whenCreate_thenThrowMissingParamException() {
        givenInvalidRequest();

        assertThrows(MissingParameterException.class, () -> reservationController.reserve(reservationRequest));
    }

    @Test
    void givenValidRequest_whenReserve_thenReserveForGroup() {
        givenRequestIsValid();

        reservationController.reserve(reservationRequest);

        verify(reservationService).reserve(SOME_RESERVATION_NAME, SOME_RESERVATION_SIZE);
    }

    @Test
    void whenCreate_thenReturnOkStatus() {
        givenRequestIsValid();

        var response = reservationController.reserve(reservationRequest);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void whenRead_thenDelegateReadToReservationService() {
        reservationController.getAll();

        verify(reservationService).getReservations();
    }

    @Test
    void whenRead_thenReturnOkResponse() {
        var response = reservationController.getAll();

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    private void givenRequestIsValid() {
        reservationRequest = new ReservationRequest(SOME_RESERVATION_NAME, SOME_RESERVATION_SIZE);
    }

    private void givenInvalidRequest() {
        reservationRequest = new ReservationRequest(SOME_RESERVATION_NAME, null);
    }
}
