package ca.ulaval.glo4002.cafe.domain.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.InvalidCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InvalidGroupSizeException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.NoReservationFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopTest {
    public static final String SOME_SHOP_NAME = "Hiss Kaffee";
    private static final int GROUP_SIZE_HALF_SHOP_TOTAL_SEATS = 2;
    private static final int SHOP_TOTAL_SEATS = 4;
    private static final int INVALID_RESERVATION_SIZE = 1;
    private static final String SOME_RESERVATION_NAME = "MyAwesomeReservation";
    private static final String SOME_OTHER_RESERVATION_NAME = "AwesomeMyReservation";
    private static final String SOME_CUBE_NAME = "Bloom";
    private static final Integer SOME_SEAT_NUMBER = 1;
    private static final Integer OTHER_SEAT_NUMBER = 2;
    private static final CustomerId SOME_CUSTOMER_ID = new CustomerId(UUID.randomUUID().toString());
    private static final CustomerId OTHER_CUSTOMER_ID = new CustomerId(UUID.randomUUID().toString());

    private Cube cube;

    private Shop shop;

    @BeforeEach
    void setUpShopSingleCubeFourSeats() {
        final var firstSeat = new Seat(1);
        final var secondSeat = new Seat(2);
        final var thirdSeat = new Seat(3);
        final var fourthSeat = new Seat(4);
        cube = new Cube(SOME_CUBE_NAME, List.of(firstSeat, secondSeat, thirdSeat, fourthSeat));
        shop = new Shop(SOME_SHOP_NAME, List.of(cube));
    }

    @Test
    void givenNameAndCubes_whenCreatingShop_thenShopShouldHaveSpecifiedProperties() {
        assertEquals(SOME_SHOP_NAME, shop.getName());
        assertTrue(shop.getCubes().contains(cube));
    }

    @Test
    void givenShopWithCubes_whenGettingNumberOfCubes_thenReturnNumberOfCubesInShop() {
        var numberOfCubes = shop.getNumberOfCubes();

        assertEquals(1, numberOfCubes);
    }

    @Test
    void givenNameOfDefaultCube_whenCheckingIfShopHasCubeWithMatchingName_thenReturnTrue() {
        var hasCubeNamed = shop.hasCubeNamed(SOME_CUBE_NAME);

        assertTrue(hasCubeNamed);
    }

    @Test
    void givenAReservationName_whenAssigningCustomer_thenThereShouldBeOneLessSeatReservedForReservation() {
        shop.reserve(SOME_RESERVATION_NAME, GROUP_SIZE_HALF_SHOP_TOTAL_SEATS);

        shop.assign(SOME_CUSTOMER_ID, SOME_RESERVATION_NAME);

        var availableSeats = shop.getReservedButNonOccupiedSeats(SOME_RESERVATION_NAME);
        assertEquals(GROUP_SIZE_HALF_SHOP_TOTAL_SEATS - 1, availableSeats.size());
    }

    @Test
    void givenNullReservationName_whenAssigningCustomer_thenThereShouldBeOneLessAvailableSeat() {
        shop.assign(SOME_CUSTOMER_ID, null);

        var availableSeats = shop.checkAllCubesToGetAvailableSeats();
        assertEquals(SHOP_TOTAL_SEATS - 1, availableSeats.size());
    }

    @Test
    void givenNullReservationNameAndNoAvailableSeatsLeft_whenAssigningCustomer_thenThrowInsufficientSeatsException() {
        var seatedCustomerId = new CustomerId(UUID.randomUUID().toString());
        shop = givenShopWithOneEmptySeat();
        shop.assign(seatedCustomerId, null);

        assertThrows(InsufficientSeatsException.class, () -> shop.assign(SOME_CUSTOMER_ID, null));
    }

    @Test
    void givenSeatedCustomer_whenRetrievingCustomerSeat_thenReturnNumberOfCustomerSeat() {
        shop.assign(SOME_CUSTOMER_ID, null);

        Seat seat = new Seat(SOME_SEAT_NUMBER);

        var expected = (CustomerInformation) seat;
        var actual = shop.retrieveCustomerSeatInformation(SOME_CUSTOMER_ID);

        assertEquals(expected.getSeatNumber(), actual.getSeatNumber());
    }

    @Test
    void givenNoSeatedCustomer_whenRetrievingCustomerSeat_thenThrowInvalidCustomerIdException() {
        assertThrows(InvalidCustomerIdException.class, () -> shop.retrieveCustomerSeatInformation(SOME_CUSTOMER_ID));
    }

    @Test
    void givenSeatedCustomer_whenFreeingAllCubes_thenFreeingAllSeatsOfCubes() {
        shop.assign(SOME_CUSTOMER_ID, null);

        shop.freeAllCubes();

        assertEquals(SHOP_TOTAL_SEATS, shop.checkAllCubesToGetAvailableSeats().size());
    }

    @Test
    void givenCustomerAssigned_whenFreeSeat_thenSeatIsAvailable() {
        var seat = new Seat(SOME_SEAT_NUMBER);
        var cubes = List.of(new Cube(SOME_CUBE_NAME, List.of(seat)));
        shop = new Shop(SOME_SHOP_NAME, cubes);
        shop.assign(SOME_CUSTOMER_ID, null);

        shop.freeSeat(SOME_CUSTOMER_ID);

        assertTrue(seat.isAvailable());
    }

    @Test
    void givenEmptyShopAndCustomerWithInvalidReservation_whenAssign_thenThrowsNoReservationFoundException() {
        assertThrows(NoReservationFoundException.class, () -> shop.assign(SOME_CUSTOMER_ID, SOME_RESERVATION_NAME));
    }

    @Test
    void givenShopReservedByGroupButAllSeatsOccupied_whenAssign_thenThrowsNoGroupSeatsException() {
        var firstSeatedCustomerId = new CustomerId(UUID.randomUUID().toString());
        var secondSeatedCustomerId = new CustomerId(UUID.randomUUID().toString());
        shop.reserve(SOME_RESERVATION_NAME, 2);
        shop.assign(firstSeatedCustomerId, SOME_RESERVATION_NAME);
        shop.assign(secondSeatedCustomerId, SOME_RESERVATION_NAME);

        assertThrows(NoGroupSeatsException.class, () -> shop.assign(SOME_CUSTOMER_ID, SOME_RESERVATION_NAME));
    }

    @Test
    void givenEmptyShopAndReservationWithInvalidSize_whenReserve_thenThrowsInvalidGroupSizeException() {
        assertThrows(InvalidGroupSizeException.class,
                     () -> shop.reserve(SOME_RESERVATION_NAME, INVALID_RESERVATION_SIZE));
    }

    @Test
    void givenReservationDuplication_whenReserve_thenThrowsDuplicateGroupNameException() {
        shop.reserve(SOME_RESERVATION_NAME, GROUP_SIZE_HALF_SHOP_TOTAL_SEATS);

        assertThrows(DuplicateGroupNameException.class, () -> shop.reserve(SOME_RESERVATION_NAME,
                                                                           GROUP_SIZE_HALF_SHOP_TOTAL_SEATS));
    }

    @Test
    void givenReservations_whenGetReservations_thenReturnsReservationsInformation() {
        List<ReservationInformation> expectedReservationInfo = List.of(
                new ReservationInformation(SOME_RESERVATION_NAME, GROUP_SIZE_HALF_SHOP_TOTAL_SEATS),
                new ReservationInformation(SOME_OTHER_RESERVATION_NAME, GROUP_SIZE_HALF_SHOP_TOTAL_SEATS)
        );
        shop.reserve(SOME_RESERVATION_NAME, GROUP_SIZE_HALF_SHOP_TOTAL_SEATS);
        shop.reserve(SOME_OTHER_RESERVATION_NAME, GROUP_SIZE_HALF_SHOP_TOTAL_SEATS);

        List<ReservationInformation> actualReservationInfo = shop.getReservations();

        assertEquals(expectedReservationInfo, actualReservationInfo);
    }

    @Test
    void givenPenultimateCustomerInReservation_whenFreeSeat_thenReservationRemain() {
        List<Seat> seats = new ArrayList<>();
        Seat lastSeat = new Seat(SOME_SEAT_NUMBER);
        seats.add(lastSeat);
        seats.add(new Seat(OTHER_SEAT_NUMBER));
        var cubes = List.of(new Cube(SOME_CUBE_NAME, seats));
        shop = new Shop(SOME_SHOP_NAME, cubes);
        shop.reserve(SOME_RESERVATION_NAME, GROUP_SIZE_HALF_SHOP_TOTAL_SEATS);
        shop.assign(SOME_CUSTOMER_ID, SOME_RESERVATION_NAME);
        shop.assign(OTHER_CUSTOMER_ID, SOME_RESERVATION_NAME);

        shop.freeSeat(OTHER_CUSTOMER_ID);

        assertTrue(lastSeat.isReserved());
    }

    @Test
    void givenLastCustomerInReservation_whenFreeSeat_thenSeatsOfReservationAreAvailable() {
        List<Seat> seats = new ArrayList<>();
        Seat lastSeat = new Seat(SOME_SEAT_NUMBER);
        Seat penultimateSeat = new Seat(OTHER_SEAT_NUMBER);
        seats.add(lastSeat);
        seats.add(penultimateSeat);
        var cubes = List.of(new Cube(SOME_CUBE_NAME, seats));
        shop = new Shop(SOME_SHOP_NAME, cubes);
        shop.reserve(SOME_RESERVATION_NAME, GROUP_SIZE_HALF_SHOP_TOTAL_SEATS);
        shop.assign(SOME_CUSTOMER_ID, SOME_RESERVATION_NAME);

        shop.freeSeat(SOME_CUSTOMER_ID);

        assertTrue(lastSeat.isAvailable());
        assertTrue(penultimateSeat.isAvailable());
    }

    private Shop givenShopWithOneEmptySeat() {
        var seat = new Seat(1);
        var cube = new Cube(SOME_CUBE_NAME, List.of(seat));
        return new Shop(SOME_SHOP_NAME, List.of(cube));
    }
}
