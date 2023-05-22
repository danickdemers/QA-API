package ca.ulaval.glo4002.cafe.domain.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeatTest {
    public static final int SEAT_NUMBER = 1;
    private static final CustomerId CUSTOMER_ID = new CustomerId("aaaaa-4fce-44a0-aa41-c41c5777e679");
    private static final String RESERVATION_NAME = "RESERVATION_NAME";

    private Seat seat;

    @BeforeEach
    void setUp() {
        seat = new Seat(SEAT_NUMBER);
    }

    @Test
    void whenAssign_thenSpecifiedValuesAreAssignedToSeat() {
        seat.assign(CUSTOMER_ID, RESERVATION_NAME);

        assertEquals(CUSTOMER_ID, seat.getCustomerId());
        assertEquals(RESERVATION_NAME, seat.getReservationName());
    }

    @Test
    void givenAssignedSeat_whenFree_thenSeatValuesAreFreed() {
        seat.assign(CUSTOMER_ID, RESERVATION_NAME);

        seat.free();

        assertNull(seat.getCustomerId());
        assertNull(seat.getReservationName());
    }

    @Test
    void givenAssignedSeat_whenIsAvailable_thenReturnFalse() {
        seat.assign(CUSTOMER_ID, RESERVATION_NAME);

        var isAvailable = seat.isAvailable();

        assertFalse(isAvailable);
    }

    @Test
    void givenReservedSeat_whenIsAvailable_thenReturnFalse() {
        seat.reserve(RESERVATION_NAME);

        var isAvailable = seat.isAvailable();

        assertFalse(isAvailable);
    }

    @Test
    void givenFreeSeat_whenIsAvailable_thenReturnTrue() {
        var isAvailable = seat.isAvailable();

        assertTrue(isAvailable);
    }

    @Test
    void givenAssignedSeat_whenIsReserved_thenReturnTrue() {
        seat.assign(CUSTOMER_ID, RESERVATION_NAME);

        var isReserved = seat.isReserved();

        assertTrue(isReserved);
    }

    @Test
    void givenReservedSeat_whenIsReserved_thenReturnTrue() {
        seat.reserve(RESERVATION_NAME);

        var isReserved = seat.isReserved();

        assertTrue(isReserved);
    }

    @Test
    void givenFreeSeat_whenIsReserved_thenReturnFalse() {
        var isReserved = seat.isReserved();

        assertFalse(isReserved);
    }

    @Test
    void givenAssignedSeat_whenIsOccupied_thenReturnTrue() {
        seat.assign(CUSTOMER_ID, RESERVATION_NAME);

        var isOccupied = seat.isOccupied();

        assertTrue(isOccupied);
    }

    @Test
    void givenReservedSeat_whenIsOccupied_thenReturnFalse() {
        seat.reserve(RESERVATION_NAME);

        var isOccupied = seat.isOccupied();

        assertFalse(isOccupied);
    }

    @Test
    void givenFreeSeat_whenIsOccupied_thenReturnFalse() {
        var isOccupied = seat.isOccupied();

        assertFalse(isOccupied);
    }

    @Test
    void givenFreeSeat_thenSeatStatusIsAvailable() {
        assertEquals(Status.AVAILABLE.getName(), seat.getStatus());
    }

    @Test
    void givenReservedSeat_thenSeatStatusIsReserved() {
        seat.reserve(RESERVATION_NAME);

        assertEquals(Status.RESERVED.getName(), seat.getStatus());
    }

    @Test
    void givenAssignedSeat_thenSeatStatusIsOccupied() {
        seat.assign(CUSTOMER_ID, RESERVATION_NAME);

        assertEquals(Status.OCCUPIED.getName(), seat.getStatus());
    }

    private enum Status {
        AVAILABLE("Available"),
        RESERVED("Reserved"),
        OCCUPIED("Occupied");
        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
