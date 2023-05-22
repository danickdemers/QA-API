package ca.ulaval.glo4002.cafe.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.shop.Shop;
import ca.ulaval.glo4002.cafe.domain.shop.ShopFactory;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShopInitializerTest {
    @Mock
    private Shop shop;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private ShopFactory shopFactory;
    @Mock
    private IngredientInventoryRepository ingredientInventoryRepository;

    private ShopInitializer shopInitializer;

    @BeforeEach
    void setUp() {
        shopInitializer = new ShopInitializer(shopRepository, shopFactory, ingredientInventoryRepository);
    }

    @Test
    public void whenInitializingCoffeeShop_thenDelegateShopSavingToShopRepository() {
        given(shopFactory.create()).willReturn(shop);

        shopInitializer.initializeCoffeeShop();

        verify(shopRepository).save(shop);
    }
}
