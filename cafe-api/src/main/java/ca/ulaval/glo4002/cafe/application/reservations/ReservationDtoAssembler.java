package ca.ulaval.glo4002.cafe.application.reservations;

import java.util.List;

import ca.ulaval.glo4002.cafe.application.reservations.dtos.ReservationDto;
import ca.ulaval.glo4002.cafe.application.reservations.dtos.ReservationsDto;
import ca.ulaval.glo4002.cafe.domain.shop.ReservationInformation;

public class ReservationDtoAssembler {
    public ReservationsDto reservationsToDto(List<ReservationInformation> info) {
        return new ReservationsDto(info.stream().map(this::reservationToDto).toList());
    }

    private ReservationDto reservationToDto(ReservationInformation info) {
        return new ReservationDto(info.reservationName(), info.reservationSize());
    }
}
