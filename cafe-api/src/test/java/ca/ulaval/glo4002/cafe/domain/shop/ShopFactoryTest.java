package ca.ulaval.glo4002.cafe.domain.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.config.ShopConfiguration;

import static ca.ulaval.glo4002.cafe.config.CubeNames.BLOOM;
import static ca.ulaval.glo4002.cafe.config.CubeNames.MERRYWEATHER;
import static ca.ulaval.glo4002.cafe.config.CubeNames.TINKER_BELL;
import static ca.ulaval.glo4002.cafe.config.CubeNames.WANDA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ShopFactoryTest {
    private ShopFactory shopFactory;

    @BeforeEach
    void setUp() {
        shopFactory = new ShopFactory();
    }

    @Test
    void whenCreatingShop_thenCreateShopWithAsManyCubesAsDefinedInShopConfiguration() {
        var shop = shopFactory.create();

        assertEquals(ShopConfiguration.NUMBER_OF_CUBES, shop.getNumberOfCubes());
    }

    @Test
    void whenCreatingShop_thenCreateShopWithAsManySeatsPerCubeAsDefinedInShopConfiguration() {
        var shop = shopFactory.create();

        assertTrue(shop.getCubes().stream().allMatch(x -> x.numberOfSeats() == ShopConfiguration.NB_SEATS_PER_CUBE));
    }

    @Test
    void whenCreatingShop_thenCreatedShopCubesAreNamedAccordingToConfiguredCubeNames() {
        var shop = shopFactory.create();

        assertTrue(shop.hasCubeNamed(BLOOM.getName()));
        assertTrue(shop.hasCubeNamed(MERRYWEATHER.getName()));
        assertTrue(shop.hasCubeNamed(TINKER_BELL.getName()));
        assertTrue(shop.hasCubeNamed(WANDA.getName()));
    }

    @Test
    void whenCreatingShop_thenCreateShopWithCoffeeShopNameDefinedInShopConfiguration() {
        var shop = shopFactory.create();

        assertEquals(ShopConfiguration.COFFEE_SHOP_NAME, shop.getName());
    }
}
