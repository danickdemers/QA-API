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

class FullCubeReservationStrategyTest {
    private static final String A_RESERVATION_NAME = "Full boy";
    private static final int A_RESERVATION_SIZE = 4;
    private Seat seat1;
    private Seat seat2;
    private Seat seat3;
    private Seat seat4;
    private Seat seat5;
    private Seat seat6;
    private Seat seat7;
    private Seat seat8;
    private Seat seat9;
    private Cube firstCube;
    private Cube secondCube;
    private Cube thirdCube;
    private FullCubeReservationStrategy strategy;
    private List<Cube> cubes;

    @BeforeEach
    void setup() {
        strategy = new FullCubeReservationStrategy();
        initialiseSeats();
        initialiseCubes();
        cubes = List.of(firstCube, secondCube, thirdCube);
    }

    @Test
    void givenShopWithAllFreeSeats_whenReserveSomeSeats_thenReserveFirstCube() {
        when(firstCube.areAllSeatsAvailable()).thenReturn(true);
        when(secondCube.areAllSeatsAvailable()).thenReturn(true);
        when(thirdCube.areAllSeatsAvailable()).thenReturn(true);
        when(firstCube.getAvailableSeats()).thenReturn(List.of(seat1, seat2, seat3));
        when(secondCube.getAvailableSeats()).thenReturn(List.of(seat4, seat5, seat6));
        when(thirdCube.getAvailableSeats()).thenReturn(List.of(seat7, seat8, seat9));
        when(firstCube.getSeats()).thenReturn(List.of(seat1, seat2, seat3));
        when(secondCube.getSeats()).thenReturn(List.of(seat4, seat5, seat6));
        when(thirdCube.getSeats()).thenReturn(List.of(seat7, seat8, seat9));

        strategy.reserve(A_RESERVATION_NAME, A_RESERVATION_SIZE, cubes);

        verify(seat1).reserve(A_RESERVATION_NAME);
        verify(seat2).reserve(A_RESERVATION_NAME);
        verify(seat3).reserve(A_RESERVATION_NAME);
        verify(seat4).reserve(A_RESERVATION_NAME);
        verify(seat5).reserve(A_RESERVATION_NAME);
        verify(seat6).reserve(A_RESERVATION_NAME);
        verify(seat7, never()).reserve(anyString());
        verify(seat8, never()).reserve(anyString());
        verify(seat9, never()).reserve(anyString());
    }

    @Test
    void givenShopWithSomeFreeSeats_whenReserveSomeSeats_thenReserveRightSeats() {
        when(firstCube.areAllSeatsAvailable()).thenReturn(false);
        when(secondCube.areAllSeatsAvailable()).thenReturn(true);
        when(thirdCube.areAllSeatsAvailable()).thenReturn(true);
        when(firstCube.getAvailableSeats()).thenReturn(List.of(seat2));
        when(secondCube.getAvailableSeats()).thenReturn(List.of(seat4, seat5, seat6));
        when(thirdCube.getAvailableSeats()).thenReturn(List.of(seat7, seat8, seat9));
        when(firstCube.getSeats()).thenReturn(List.of(seat1, seat2, seat3));
        when(secondCube.getSeats()).thenReturn(List.of(seat4, seat5, seat6));
        when(thirdCube.getSeats()).thenReturn(List.of(seat7, seat8, seat9));

        strategy.reserve(A_RESERVATION_NAME, A_RESERVATION_SIZE, cubes);

        verify(seat1, never()).reserve(anyString());
        verify(seat2, never()).reserve(anyString());
        verify(seat3, never()).reserve(anyString());
        verify(seat4).reserve(A_RESERVATION_NAME);
        verify(seat5).reserve(A_RESERVATION_NAME);
        verify(seat6).reserve(A_RESERVATION_NAME);
        verify(seat7).reserve(A_RESERVATION_NAME);
        verify(seat8).reserve(A_RESERVATION_NAME);
        verify(seat9).reserve(A_RESERVATION_NAME);
    }

    @Test
    void givenShopWithNoFreeCubes_whenReserveSomeSeats_thenThrowInsufficientSeatsException() {
        when(firstCube.getAvailableSeats()).thenReturn(List.of(seat2, seat3));
        when(secondCube.getAvailableSeats()).thenReturn(List.of(seat5, seat6));

        assertThrows(InsufficientSeatsException.class,
                     () -> strategy.reserve(A_RESERVATION_NAME, A_RESERVATION_SIZE, cubes));
    }

    private void initialiseCubes() {
        firstCube = mock(Cube.class);
        secondCube = mock(Cube.class);
        thirdCube = mock(Cube.class);
    }

    private void initialiseSeats() {
        seat1 = mock(Seat.class);
        seat2 = mock(Seat.class);
        seat3 = mock(Seat.class);
        seat4 = mock(Seat.class);
        seat5 = mock(Seat.class);
        seat6 = mock(Seat.class);
        seat7 = mock(Seat.class);
        seat8 = mock(Seat.class);
        seat9 = mock(Seat.class);
    }
}
