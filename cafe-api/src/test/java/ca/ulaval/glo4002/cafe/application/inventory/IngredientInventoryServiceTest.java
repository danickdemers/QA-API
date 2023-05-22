package ca.ulaval.glo4002.cafe.application.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.IngredientId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientInventoryServiceTest {
    private static final int CHOCOLATE_QUANTITY = 2;
    private static final int ESPRESSO_QUANTITY = 100;
    private static final int MILK_QUANTITY = 100;
    private static final int WATER_QUANTITY = 9;

    private static final IngredientId INGREDIENT_ID = new IngredientId("Chocolate");

    private static final int SOME_QUANTITY = 2;
    private static final int SOME_OTHER_QUANTITY = 10;
    private static final String SOME_INGREDIENT_NAME = "Chocolate";

    private static final Ingredient SOME_INGREDIENT = new Ingredient(SOME_INGREDIENT_NAME, INGREDIENT_ID,
                                                                     SOME_QUANTITY);

    @Mock
    private IngredientInventoryRepository ingredientInventoryRepository;

    private IngredientInventoryService ingredientInventoryService;

    @BeforeEach
    void setUp() {
        ingredientInventoryService = new IngredientInventoryService(ingredientInventoryRepository);
    }

    @Test
    void givenRepositoryDoesNotAlreadyHaveIngredientInventory_whenAddInventoryToIngredients_thenAddToRepository() {
        ingredientInventoryService.addInventoryToIngredients(CHOCOLATE_QUANTITY, ESPRESSO_QUANTITY, MILK_QUANTITY,
                                                             WATER_QUANTITY);

        verify(ingredientInventoryRepository, times(4)).add(any());
    }

    @Test
    void givenRepositoryAlreadyContainsIngredientInventory_whenAddInventoryToIngredients_thenUpdateInventory() {
        when(ingredientInventoryRepository.hasInventoryForIngredient(any())).thenReturn(true);
        when(ingredientInventoryRepository.findById(any())).thenReturn(SOME_INGREDIENT);

        ingredientInventoryService.addInventoryToIngredients(CHOCOLATE_QUANTITY, ESPRESSO_QUANTITY, MILK_QUANTITY,
                                                             WATER_QUANTITY);

        verify(ingredientInventoryRepository, times(4)).update(any());
    }

    @Test
    void givenAllIngredientHaveInventoryInRepository_whenGetIngredientsInventory_thenUpdateInventory() {
        ingredientInventoryService.addInventoryToIngredients(CHOCOLATE_QUANTITY, ESPRESSO_QUANTITY, MILK_QUANTITY,
                                                             WATER_QUANTITY);
        when(ingredientInventoryRepository.findById(any())).thenReturn(SOME_INGREDIENT);
        var inventory = ingredientInventoryService.getIngredientsInventory();

        assertEquals(SOME_QUANTITY, inventory.chocolate);
        assertEquals(SOME_QUANTITY, inventory.espresso);
        assertEquals(SOME_QUANTITY, inventory.milk);
        assertEquals(SOME_QUANTITY, inventory.water);
    }
}
