package ca.ulaval.glo4002.cafe.application.shop;

import java.util.List;

import ca.ulaval.glo4002.cafe.application.shop.dtos.SeatDto;
import ca.ulaval.glo4002.cafe.domain.shop.Seat;

public class SeatDtoAssembler {
    public SeatDto seatToDto(Seat seat) {
        var customerId = seat.getCustomerIdValue();

        return new SeatDto(seat.getSeatNumber(), seat.getStatus(), customerId, seat.getReservationName());
    }

    public List<SeatDto> seatsToDtos(List<Seat> seats) {
        return seats.stream().map(this::seatToDto).toList();
    }
}
