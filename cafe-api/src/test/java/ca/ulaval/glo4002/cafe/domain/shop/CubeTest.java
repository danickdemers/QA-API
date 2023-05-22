package ca.ulaval.glo4002.cafe.domain.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CubeTest {
    private static final String SOME_CUBE_NAME = "BLOOM";
    private static final String SOME_RESERVATION_NAME = "MyReservation";
    private static final String SOME_OTHER_RESERVATION_NAME = "MyOtherReservation";
    @Mock
    private Seat seat;
    private List<Seat> seats;

    private Cube cube;

    @BeforeEach
    void setUp() {
        seats = new ArrayList<>();
    }

    @Test
    void givenCubeWithSeats_whenGettingNumberOfSeats_thenReturnNumberOfSeatsInCube() {
        seats.add(new Seat(1));
        seats.add(new Seat(2));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var numberOfSeats = cube.numberOfSeats();

        assertEquals(2, numberOfSeats);
    }

    @Test
    void givenCubeWithReservedSeats_whenGetReservedSeatsForReservation_thenReturnAsManySeatsAsThereAreReservedSeats() {
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(new Seat(1));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var reservedSeats = cube.getReservedSeatsForReservation(SOME_RESERVATION_NAME);

        assertEquals(2, reservedSeats.size());
    }

    @Test
    void givenCubeWithReservedSeats_whenGetReservedSeatsForReservation_thenAllReturnedSeatsAreReserved() {
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(new Seat(1));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var reservedSeats = cube.getReservedSeatsForReservation(SOME_RESERVATION_NAME);

        assertTrue(reservedSeats.stream().allMatch(Seat::isReserved));
    }

    @Test
    void givenCubeWithReservedSeats_whenGetReservedSeatsForReservation_thenReturnedSeatsAreForSpecifiedReservation() {
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(getReservedSeat(SOME_OTHER_RESERVATION_NAME));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var reservedSeats = cube.getReservedSeatsForReservation(SOME_RESERVATION_NAME);

        assertTrue(reservedSeats.stream().allMatch(x -> Objects.equals(x.getReservationName(), SOME_RESERVATION_NAME)));
    }

    @Test
    void givenCubeWithReservedAndOccupiedSeats_whenGetNonOccupiedAndReservedSeatsForReservation_thenReturnedSeatsShouldBeReserved() {
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(getOccupiedSeat());
        cube = new Cube(SOME_CUBE_NAME, seats);

        var reservedButNotOccupiedSeats = cube.getReservedButNonOccupiedSeats(SOME_RESERVATION_NAME);

        assertTrue(reservedButNotOccupiedSeats.stream().anyMatch(Seat::isReserved));
    }

    @Test
    void givenCubeWithReservedAndOccupiedSeats_whenGetNonOccupiedAndReservedSeatsForReservation_thenReturnedSeatsShouldNotBeOccupied() {
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(getOccupiedSeat());
        cube = new Cube(SOME_CUBE_NAME, seats);

        var reservedButNotOccupiedSeats = cube.getReservedButNonOccupiedSeats(SOME_RESERVATION_NAME);

        assertFalse(reservedButNotOccupiedSeats.stream().anyMatch(Seat::isOccupied));
    }

    @Test
    void givenCubeWithReservedAndOccupiedSeats_whenGetNonOccupiedAndReservedSeatsForReservation_thenReturnedSeatsAreForSpecifiedReservation() {
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(getOccupiedSeat());
        cube = new Cube(SOME_CUBE_NAME, seats);

        var reservedButNotOccupiedSeats = cube.getReservedButNonOccupiedSeats(SOME_RESERVATION_NAME);

        assertTrue(reservedButNotOccupiedSeats.stream()
                           .allMatch(x -> Objects.equals(x.getReservationName(), SOME_RESERVATION_NAME)));
    }

    @Test
    void givenCubeWithAvailableSeats_whenGetAvailableSeats_thenReturnAsManySeatsAsThereAreAvailableSeats() {
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(new Seat(1));
        seats.add(new Seat(1));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var availableSeats = cube.getAvailableSeats();

        assertEquals(2, availableSeats.size());
    }

    @Test
    void givenCubeWithAllAvailableSeats_whenAreAllSeatsAvailable_thenReturnTrue() {
        seats.add(new Seat(1));
        seats.add(new Seat(1));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var allSeatsAreAvailable = cube.areAllSeatsAvailable();

        assertTrue(allSeatsAreAvailable);
    }

    @Test
    void givenCubeWithSomeAvailableSeats_whenAreAllSeatsAvailable_thenReturnFalse() {
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(new Seat(1));
        seats.add(new Seat(1));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var allSeatsAreAvailable = cube.areAllSeatsAvailable();

        assertFalse(allSeatsAreAvailable);
    }

    @Test
    void givenCubeWithAvailableSeats_whenGetAvailableSeats_thenAllReturnedSeatsAreAvailable() {
        seats.add(getReservedSeat(SOME_RESERVATION_NAME));
        seats.add(new Seat(1));
        seats.add(new Seat(1));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var availableSeats = cube.getAvailableSeats();

        assertTrue(availableSeats.stream().allMatch(Seat::isAvailable));
    }

    @Test
    void givenCubeWithOccupiedSeats_whenGetOccupiedSeats_thenReturnAsManySeatsAsThereAreOccupiedSeats() {
        seats.add(getOccupiedSeat());
        seats.add(getOccupiedSeat());
        seats.add(new Seat(1));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var occupiedSeats = cube.getOccupiedSeats();

        assertEquals(2, occupiedSeats.size());
    }

    @Test
    void givenCubeWithOccupiedSeats_whenGetOccupiedSeats_thenAllReturnedSeatsAreOccupied() {
        seats.add(getOccupiedSeat());
        seats.add(getOccupiedSeat());
        seats.add(new Seat(1));
        cube = new Cube(SOME_CUBE_NAME, seats);

        var occupiedSeats = cube.getOccupiedSeats();

        assertTrue(occupiedSeats.stream().allMatch(Seat::isOccupied));
    }

    @Test
    void givenCubeWithSeats_whenFreeSeats_thenDelegateFreeingSeatsToEachSeat() {
        seats.add(seat);
        cube = new Cube(SOME_CUBE_NAME, seats);

        cube.freeAllSeats();

        verify(seat).free();
    }

    private static Seat getOccupiedSeat() {
        var seat = new Seat(1);
        seat.assign(new CustomerId(UUID.randomUUID().toString()), SOME_RESERVATION_NAME);
        return seat;
    }

    private static Seat getReservedSeat(String reservationName) {
        var seat = new Seat(1);
        seat.reserve(reservationName);
        return seat;
    }
}
