package ca.ulaval.glo4002.cafe.infrastructure.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.shop.ShopBuilder;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShopRepositoryInMemoryTest {
    private static final int SHOP_NB_SEATS_PER_CUBE = 4;
    private static final String SHOP_NAME = "Hiss Kaffee";
    private ShopBuilder shopBuilder;
    private ShopRepository shopRepository;

    @BeforeEach
    public void setUp() {
        shopBuilder = new ShopBuilder(SHOP_NB_SEATS_PER_CUBE);
        shopRepository = new ShopRepositoryInMemory();
    }

    @Test
    void givenAShopInRepository_whenGettingShop_thenReturnShop() {
        var expectedShop = shopBuilder.build(SHOP_NAME);
        shopRepository.save(expectedShop);

        var actualShop = shopRepository.getShop();

        assertEquals(expectedShop.getName(), actualShop.getName());
    }

    @Test
    void givenNoShop_whenSavingShop_thenRepositoryHasShop() {
        var shop = shopBuilder.build(SHOP_NAME);

        shopRepository.save(shop);

        assertNotNull(shopRepository.getShop());
    }
}
