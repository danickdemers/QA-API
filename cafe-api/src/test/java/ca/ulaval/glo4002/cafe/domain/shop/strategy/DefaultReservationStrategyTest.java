package ca.ulaval.glo4002.cafe.domain.shop.strategy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.shop.Cube;
import ca.ulaval.glo4002.cafe.domain.shop.Seat;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InsufficientSeatsException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultReservationStrategyTest {

    private static final String A_RESERVATION_NAME = "Default boy";
    private static final int A_RESERVATION_SIZE = 4;
    private Seat seat1;
    private Seat seat2;
    private Seat seat3;
    private Seat seat4;
    private Seat seat5;
    private Seat seat6;
    private Cube firstCube;
    private Cube secondCube;
    private List<Cube> cubes;
    private DefaultReservationStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new DefaultReservationStrategy();
        initialiseSeats();
        initialiseCubes();
        cubes = List.of(firstCube, secondCube);
    }

    @Test
    void givenShopWithAllFreeSeats_whenReserveSomeSeats_thenReserveFirstSeats() {
        when(firstCube.getAvailableSeats()).thenReturn(List.of(seat1, seat2, seat3));
        when(secondCube.getAvailableSeats()).thenReturn(List.of(seat4, seat5, seat6));

        strategy.reserve(A_RESERVATION_NAME, A_RESERVATION_SIZE, cubes);

        verify(seat1).reserve(A_RESERVATION_NAME);
        verify(seat2).reserve(A_RESERVATION_NAME);
        verify(seat3).reserve(A_RESERVATION_NAME);
        verify(seat4).reserve(A_RESERVATION_NAME);
        verify(seat5, never()).reserve(anyString());
        verify(seat6, never()).reserve(anyString());
    }

    @Test
    void givenShopWithSomeFreeSeats_whenReserveSomeSeats_thenReserveRightSeats() {
        when(firstCube.getAvailableSeats()).thenReturn(List.of(seat2));
        when(secondCube.getAvailableSeats()).thenReturn(List.of(seat4, seat5, seat6));

        strategy.reserve(A_RESERVATION_NAME, A_RESERVATION_SIZE, cubes);

        verify(seat1, never()).reserve(anyString());
        verify(seat2).reserve(A_RESERVATION_NAME);
        verify(seat3, never()).reserve(anyString());
        verify(seat4).reserve(A_RESERVATION_NAME);
        verify(seat5).reserve(A_RESERVATION_NAME);
        verify(seat6).reserve(A_RESERVATION_NAME);
    }

    @Test
    void givenShopWithoutFreeSeats_whenReserveSomeSeats_thenThrowInsufficientSeatsException() {
        when(firstCube.getAvailableSeats()).thenReturn(List.of());
        when(secondCube.getAvailableSeats()).thenReturn(List.of());

        assertThrows(InsufficientSeatsException.class,
                     () -> strategy.reserve(A_RESERVATION_NAME, A_RESERVATION_SIZE,
                                            cubes));
    }

    private void initialiseCubes() {
        firstCube = mock(Cube.class);
        secondCube = mock(Cube.class);
    }

    private void initialiseSeats() {
        seat1 = mock(Seat.class);
        seat2 = mock(Seat.class);
        seat3 = mock(Seat.class);
        seat4 = mock(Seat.class);
        seat5 = mock(Seat.class);
        seat6 = mock(Seat.class);
    }
}
