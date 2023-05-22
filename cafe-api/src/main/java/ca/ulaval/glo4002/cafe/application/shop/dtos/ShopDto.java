package ca.ulaval.glo4002.cafe.application.shop.dtos;

import java.util.List;

public record ShopDto(String name, List<CubeDto> cubes) {
}
