package ca.ulaval.glo4002.cafe.application.shop.dtos;

import java.util.List;

public record CubeDto(String name, List<SeatDto> seats) {
}
