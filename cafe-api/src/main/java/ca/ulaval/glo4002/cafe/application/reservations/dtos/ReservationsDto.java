package ca.ulaval.glo4002.cafe.application.reservations.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class ReservationsDto {
    @JsonValue
    private final List<ReservationDto> groups;

    public ReservationsDto(List<ReservationDto> groups) {
        this.groups = groups;
    }

    public List<ReservationDto> getGroups() {
        return groups;
    }
}
