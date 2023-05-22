package ca.ulaval.glo4002.cafe.domain.shop.strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.cafe.domain.shop.Cube;
import ca.ulaval.glo4002.cafe.domain.shop.Seat;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InsufficientSeatsException;

public class DefaultReservationStrategy implements ReservationStrategy {
    @Override
    public void reserve(String reservationName, int reservationSize, List<Cube> cubes) {
        LinkedList<Seat> availableSeats = getAvailableSeats(cubes);

        verifySufficientNumberOfAvailableSeats(availableSeats.size(), reservationSize);

        for (int i = 0; i < reservationSize; i++) {
            availableSeats.pop().reserve(reservationName);
        }
    }

    private LinkedList<Seat> getAvailableSeats(List<Cube> cubes) {
        return cubes.stream()
                .map(Cube::getAvailableSeats)
                .flatMap(List::stream)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private void verifySufficientNumberOfAvailableSeats(int numberOfAvailableSeats, int reservationSize) {
        if (numberOfAvailableSeats < reservationSize) {
            throw new InsufficientSeatsException();
        }
    }
}
