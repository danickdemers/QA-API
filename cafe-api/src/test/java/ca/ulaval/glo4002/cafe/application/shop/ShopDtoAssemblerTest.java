package ca.ulaval.glo4002.cafe.application.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.application.shop.dtos.ShopDto;
import ca.ulaval.glo4002.cafe.domain.shop.Shop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShopDtoAssemblerTest {
    private static final String SOME_SHOP_NAME = "KAFFEE";
    @Mock
    private CubeDtoAssembler cubeDtoAssembler;
    @Mock
    private Shop shop;

    private ShopDtoAssembler shopDtoAssembler;

    @BeforeEach
    void setUp() {
        shopDtoAssembler = new ShopDtoAssembler(cubeDtoAssembler);
    }

    @Test
    void givenShop_whenShopToDto_thenAssembleShop() {
        given(shop.getName()).willReturn(SOME_SHOP_NAME);

        ShopDto shopDto = shopDtoAssembler.shopToDto(shop);

        assertEquals(SOME_SHOP_NAME, shopDto.name());
        verify(cubeDtoAssembler).cubesToDtos(shop.getCubes());
    }
}
