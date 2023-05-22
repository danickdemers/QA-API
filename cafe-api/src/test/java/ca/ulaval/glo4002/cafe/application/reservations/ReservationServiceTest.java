package ca.ulaval.glo4002.cafe.application.reservations;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.application.reservations.dtos.ReservationDto;
import ca.ulaval.glo4002.cafe.application.reservations.dtos.ReservationsDto;
import ca.ulaval.glo4002.cafe.domain.shop.ReservationInformation;
import ca.ulaval.glo4002.cafe.domain.shop.Shop;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    private static final String SOME_RESERVATION_NAME = "REE";
    private static final int SOME_RESERVATION_SIZE = 2;
    @Mock
    private Shop shop;
    @Mock
    private ShopRepository shopRepository;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        ReservationDtoAssembler reservationDtoAssembler = new ReservationDtoAssembler();
        reservationService = new ReservationService(reservationDtoAssembler, shopRepository);
    }

    @Test
    void givenReservationInShop_whenGetReservation_thenReturnsReservation() {
        givenShop();
        given(shop.getReservations()).willReturn(
                List.of(new ReservationInformation(SOME_RESERVATION_NAME, SOME_RESERVATION_SIZE))
        );
        ReservationsDto expectedReservations = new ReservationsDto(
                List.of(new ReservationDto(SOME_RESERVATION_NAME, SOME_RESERVATION_SIZE))
        );

        ReservationsDto actualReservations = reservationService.getReservations();

        assertEquals(expectedReservations.getGroups(), actualReservations.getGroups());
    }

    @Test
    void givenReservationNameAndSize_whenReserve_thenDelegateReservationToShop() {
        givenShop();

        reservationService.reserve(SOME_RESERVATION_NAME, SOME_RESERVATION_SIZE);

        verify(shop).reserve(SOME_RESERVATION_NAME, SOME_RESERVATION_SIZE);
    }

    void givenShop() {
        given(shopRepository.getShop()).willReturn(shop);
    }
}
