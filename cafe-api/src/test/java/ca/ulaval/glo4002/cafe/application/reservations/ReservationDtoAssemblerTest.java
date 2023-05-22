package ca.ulaval.glo4002.cafe.application.reservations;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.application.reservations.dtos.ReservationDto;
import ca.ulaval.glo4002.cafe.application.reservations.dtos.ReservationsDto;
import ca.ulaval.glo4002.cafe.domain.shop.ReservationInformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ReservationDtoAssemblerTest {
    private static final String RESERVATION_NAME = "Section Z3";
    private static final int GROUP_SIZE_OF_2 = 2;
    private ReservationDtoAssembler reservationDtoAssembler;

    @BeforeEach
    void setUp() {
        reservationDtoAssembler = new ReservationDtoAssembler();
    }

    @Test
    void givenReservation_whenToDto_thenDtoHasSameAttributes() {
        var expected = new ReservationsDto(List.of(new ReservationDto(RESERVATION_NAME, GROUP_SIZE_OF_2)));
        var reservationInformation = List.of(
                new ReservationInformation(RESERVATION_NAME, GROUP_SIZE_OF_2)
        );

        ReservationsDto actual = reservationDtoAssembler.reservationsToDto(reservationInformation);

        assertEquals(expected.getGroups(), actual.getGroups());
    }
}
