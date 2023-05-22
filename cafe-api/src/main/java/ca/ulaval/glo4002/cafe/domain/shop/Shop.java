package ca.ulaval.glo4002.cafe.domain.shop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.Menu;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.InvalidCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InvalidGroupSizeException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.NoReservationFoundException;
import ca.ulaval.glo4002.cafe.domain.shop.strategy.DefaultReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.shop.strategy.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.shop.strategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.shop.strategy.ReservationType;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.None.None;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

import static java.util.stream.Collectors.groupingBy;

public class Shop {
    private final String name;
    private final List<Cube> cubes;
    private ReservationStrategy reservationStrategy;
    private Taxer taxer;
    private GroupTipRate groupTipRate;
    private Menu menu;

    public Shop(String name, List<Cube> cubes) {
        this.name = name;
        this.cubes = cubes;
        this.reservationStrategy = new DefaultReservationStrategy();
        this.taxer = new None();
        this.groupTipRate = new GroupTipRate();
        this.menu = new Menu();
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public List<Cube> getCubes() {
        return cubes;
    }

    public int getNumberOfCubes() {
        return cubes.size();
    }

    public void setConfig(Taxer taxer, GroupTipRate groupTipRate) {
        this.taxer = taxer;
        this.groupTipRate = groupTipRate;
    }

    public Taxer getTaxer() {
        return taxer;
    }

    public GroupTipRate getGroupTipRate() {
        return groupTipRate;
    }

    public boolean hasCubeNamed(String cubeName) {
        return cubes.stream().anyMatch(x -> Objects.equals(x.getName(), cubeName));
    }

    public void assign(CustomerId customerId, String reservationName) {
        if (Optional.ofNullable(reservationName).isPresent()) {
            assignCustomerToAReservationSeat(customerId, reservationName);
        } else {
            assignCustomerToAnAvailableSeat(customerId);
        }
    }

    public void assignCustomerToAReservationSeat(CustomerId customerId, String reservationName) {
        var availableSeats = getReservedButNonOccupiedSeats(reservationName);

        if (!reservationNameExists(reservationName)) {
            throw new NoReservationFoundException();
        } else if (availableSeats.isEmpty()) {
            throw new NoGroupSeatsException();
        }
        availableSeats.pop().assign(customerId, reservationName);
    }

    public void assignCustomerToAnAvailableSeat(CustomerId customerId) {
        var availableSeats = checkAllCubesToGetAvailableSeats();
        if (availableSeats.isEmpty()) {
            throw new InsufficientSeatsException();
        }
        availableSeats.pop().assign(customerId, null);
    }

    public LinkedList<Seat> getReservedButNonOccupiedSeats(String reservationName) {
        return cubes.stream()
                .map(cube -> cube.getReservedButNonOccupiedSeats(reservationName))
                .flatMap(List::stream)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public LinkedList<Seat> checkAllCubesToGetAvailableSeats() {
        return cubes.stream()
                .map(Cube::getAvailableSeats)
                .flatMap(List::stream)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void reserve(String reservationName, int groupSize) {
        if (groupSize < 2) {
            throw new InvalidGroupSizeException();
        } else if (reservationNameExists(reservationName)) {
            throw new DuplicateGroupNameException();
        }
        reservationStrategy.reserve(reservationName, groupSize, cubes);
    }

    public CustomerInformation retrieveCustomerSeatInformation(CustomerId customerId) {
        return findSeat(customerId);
    }

    public List<ReservationInformation> getReservations() {
        List<ReservationInformation> reservations = new ArrayList<>();

        var reservedSeats = cubes.stream()
                .map(Cube::getReservedSeats)
                .flatMap(List::stream)
                .collect(groupingBy(Seat::getReservationName));

        reservedSeats.forEach((name, seats) -> reservations.add(new ReservationInformation(name, seats.size())));

        return reservations;
    }

    public void setReservationStrategy(ReservationType reservationType) {
        reservationStrategy = ReservationStrategyFactory.create(reservationType);
    }

    public void freeAllCubes() {
        cubes.forEach(Cube::freeAllSeats);
    }

    private Stream<Seat> checkAllCubesForOccupiedSeats() {
        return cubes.stream().map(Cube::getOccupiedSeats).flatMap(List::stream);
    }

    private boolean reservationNameExists(String reservationName) {
        return cubes.stream()
                .map(Cube::getReservedSeats)
                .flatMap(List::stream)
                .anyMatch(seat -> Objects.equals(seat.getReservationName(), reservationName));
    }

    private Seat findSeat(CustomerId customerId) {
        var customerSeat = checkAllCubesForOccupiedSeats()
                .filter(seat -> seat.getCustomerId().equals(customerId))
                .findFirst();
        if (customerSeat.isEmpty()) {
            throw new InvalidCustomerIdException();
        }
        return customerSeat.get();
    }

    private void freeEmptyReservation(String reservationName) {
        var seats = cubes.stream()
                .map(cube -> cube.getReservedSeatsForReservation(reservationName))
                .flatMap(List::stream)
                .collect(Collectors.toCollection(LinkedList::new));
        if (seats.stream().anyMatch(Seat::isOccupied)) {
            return;
        }
        for (Seat seat : seats) {
            seat.free();
        }
    }

    public void freeSeat(CustomerId customerId) {
        var seat = findSeat(customerId);
        var reservationName = seat.getReservationName();
        seat.free();
        freeEmptyReservation(reservationName);
    }
}
