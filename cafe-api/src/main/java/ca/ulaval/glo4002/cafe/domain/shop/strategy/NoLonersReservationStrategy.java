package ca.ulaval.glo4002.cafe.domain.shop.strategy;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.shop.Cube;
import ca.ulaval.glo4002.cafe.domain.shop.Seat;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InsufficientSeatsException;

public class NoLonersReservationStrategy implements ReservationStrategy {
    private static final int MINIMUM_PARTY_SIZE = 2;

    private static int seatPeopleInCube(Cube cube, int numberOfPeopleToBeSeated, List<Seat> seatsToBeReserved) {
        int numberOfPeopleSeated = 0;

        for (var seat : cube.getAvailableSeats()) {
            if (numberOfPeopleToBeSeated > numberOfPeopleSeated) {
                seatsToBeReserved.add(seat);
                numberOfPeopleSeated++;
            }
        }

        return numberOfPeopleSeated;
    }

    private void reserveSeats(List<Seat> seats, String reservationName) {
        for (Seat seat : seats) {
            seat.reserve(reservationName);
        }
    }

    @Override
    public void reserve(String reservationName, int reservationSize, List<Cube> cubes) {
        int numberOfPeopleSeated = 0;

        List<Seat> seatsToBeReserved = new ArrayList<>();

        for (var cube : cubes) {
            var numberOfPeopleToBeSeated = reservationSize - numberOfPeopleSeated;
            var numberOfAvailableSeats = cube.getAvailableSeats().size();

            if (numberOfAvailableSeats < MINIMUM_PARTY_SIZE) {
                continue;
            }

            if (numberOfAvailableSeats >= numberOfPeopleToBeSeated) {
                numberOfPeopleSeated += seatPeopleInCube(cube, numberOfPeopleToBeSeated, seatsToBeReserved);
            } else if (numberOfPeopleToBeSeated != MINIMUM_PARTY_SIZE + 1) {
                numberOfPeopleSeated += seatPeopleInCube(cube,
                                                         numberOfPeopleToBeSeated - MINIMUM_PARTY_SIZE,
                                                         seatsToBeReserved);
            }
        }

        if (numberOfPeopleSeated != reservationSize) {
            throw new InsufficientSeatsException();
        }

        reserveSeats(seatsToBeReserved, reservationName);
    }
}
