package ca.ulaval.glo4002.cafe.domain.shop.strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.cafe.domain.shop.Cube;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InsufficientSeatsException;

public class FullCubeReservationStrategy implements ReservationStrategy {
    @Override
    public void reserve(String reservationName, int reservationSize, List<Cube> cubes) {
        LinkedList<Cube> availableCubes = getAvailableCubes(cubes);
        int numberOfAvailableSeats = getNumberOfAvailableSeats(availableCubes);

        verifySufficientNumberOfAvailableSeats(numberOfAvailableSeats, reservationSize);

        for (int numberOfPeopleSeated = 0; numberOfPeopleSeated < reservationSize; ) {
            var cube = availableCubes.pop();
            cube.getAvailableSeats().forEach(seat -> seat.reserve(reservationName));
            numberOfPeopleSeated += cube.getSeats().size();
        }
    }

    private int getNumberOfAvailableSeats(LinkedList<Cube> availableCubes) {
        return availableCubes.stream()
                .map(Cube::getAvailableSeats)
                .flatMap(List::stream).toList()
                .size();
    }

    private LinkedList<Cube> getAvailableCubes(List<Cube> cubes) {
        return cubes.stream()
                .filter(Cube::areAllSeatsAvailable)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private void verifySufficientNumberOfAvailableSeats(int numberOfAvailableSeats, int reservationSize) {
        if (numberOfAvailableSeats < reservationSize) {
            throw new InsufficientSeatsException();
        }
    }
}
