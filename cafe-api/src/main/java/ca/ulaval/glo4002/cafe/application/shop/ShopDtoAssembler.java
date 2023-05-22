package ca.ulaval.glo4002.cafe.application.shop;

import ca.ulaval.glo4002.cafe.application.shop.dtos.ShopDto;
import ca.ulaval.glo4002.cafe.domain.shop.Shop;

public class ShopDtoAssembler {
    private final CubeDtoAssembler cubeDtoAssembler;

    public ShopDtoAssembler(CubeDtoAssembler cubeDtoAssembler) {
        this.cubeDtoAssembler = cubeDtoAssembler;
    }

    public ShopDto shopToDto(Shop shop) {
        return new ShopDto(shop.getName(), cubeDtoAssembler.cubesToDtos(shop.getCubes()));
    }
}
