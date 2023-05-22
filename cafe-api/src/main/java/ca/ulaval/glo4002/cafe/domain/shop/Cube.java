package ca.ulaval.glo4002.cafe.domain.shop;

import java.util.List;
import java.util.Objects;

public class Cube {
    private final String name;
    private final List<Seat> seats;

    public Cube(String name, List<Seat> seats) {
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int numberOfSeats() {
        return seats.size();
    }

    public List<Seat> getReservedSeatsForReservation(String reservationName) {
        return seats.stream()
                .filter(seat -> seat.isReserved() && Objects.equals(reservationName, seat.getReservationName()))
                .toList();
    }

    public List<Seat> getReservedButNonOccupiedSeats(String reservationName) {
        return seats.stream()
                .filter(seat -> !seat.isOccupied() && seat.isReserved())
                .filter(seat -> Objects.equals(reservationName, seat.getReservationName()))
                .toList();
    }

    public List<Seat> getReservedSeats() {
        return seats.stream()
                .filter(Seat::isReserved)
                .toList();
    }

    public List<Seat> getAvailableSeats() {
        return seats.stream()
                .filter(Seat::isAvailable)
                .toList();
    }

    public List<Seat> getOccupiedSeats() {
        return seats.stream()
                .filter(Seat::isOccupied)
                .toList();
    }

    public void freeAllSeats() {
        seats.forEach(Seat::free);
    }

    public boolean areAllSeatsAvailable() {
        return this.getAvailableSeats().size() == this.getSeats().size();
    }
}
