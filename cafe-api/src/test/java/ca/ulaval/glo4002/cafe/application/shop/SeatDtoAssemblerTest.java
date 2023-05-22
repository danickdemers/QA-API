package ca.ulaval.glo4002.cafe.application.shop;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.shop.dtos.SeatDto;
import ca.ulaval.glo4002.cafe.domain.shop.Seat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatDtoAssemblerTest {
    private SeatDtoAssembler seatDtoAssembler;
    @BeforeEach
    void setUp() {
        seatDtoAssembler = new SeatDtoAssembler();
    }

    @Test
    void givenASeat_whenMappingSeatToDto_thenReceiveSeatDto() {
        Seat seat = new Seat(1);
        SeatDto expectedSeatDto = new SeatDto(1, "Available", null, null);

        SeatDto actualSeatDto = seatDtoAssembler.seatToDto(seat);

        assertEquals(expectedSeatDto, actualSeatDto);
    }

    @Test
    void givenSeats_whenMappingSeatsToDtos_thenReturnSeatDtos() {
        List<Seat> seats = List.of(new Seat(1));
        List<SeatDto> expectedSeatDtos = List.of(new SeatDto(1, "Available", null, null));

        List<SeatDto> actualSeatDtos = seatDtoAssembler.seatsToDtos(seats);

        assertEquals(expectedSeatDtos, actualSeatDtos);
    }
}
