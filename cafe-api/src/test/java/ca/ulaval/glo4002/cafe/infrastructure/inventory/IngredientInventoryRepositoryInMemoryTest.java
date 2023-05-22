package ca.ulaval.glo4002.cafe.infrastructure.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.AlreadyPresentIngredientInventoryException;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.NonExistentIngredientInventoryException;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.IngredientId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class IngredientInventoryRepositoryInMemoryTest {
    private static final String SOME_INGREDIENT_NAME = "chocolate";
    private static final int SOME_OTHER_QUANTITY = 4;

    private IngredientInventoryRepositoryInMemory ingredientInventoryRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        ingredientInventoryRepositoryInMemory = new IngredientInventoryRepositoryInMemory();
    }

    @Test
    void givenIngredientInventoryIsInRepository_whenAdd_thenThrowAlreadyPresentIngredientInventoryException() {
        var chocolateInventory = givenChocolateInventoryInRepository();

        assertThrows(AlreadyPresentIngredientInventoryException.class,
                     () -> ingredientInventoryRepositoryInMemory.add(chocolateInventory));
    }

    @Test
    void givenIngredientInventoryIsNotInRepository_whenAdd_thenRepositoryHasInventoryIngredient() {
        var chocolateInventory = givenChocolateInventoryInRepository();

        var hasInventory = ingredientInventoryRepositoryInMemory
                .hasInventoryForIngredient(chocolateInventory.getIngredientId());
        assertTrue(hasInventory);
    }

    @Test
    void givenIngredientInventoryIsInRepository_whenFindById_thenReturnMatchingIngredientInventory() {
        var chocolateInventory = givenChocolateInventoryInRepository();
        var expectedId = chocolateInventory.getIngredientId().toString();

        var ingredientInventory = ingredientInventoryRepositoryInMemory
                .findById(chocolateInventory.getIngredientId());

        assertEquals(expectedId, ingredientInventory.getIngredientId().toString());
    }

    @Test
    void givenIngredientInventoryIsNotInRepository_whenFindById_thenThrowNonExistentIngredientInventoryException() {
        assertThrows(NonExistentIngredientInventoryException.class, () ->
                ingredientInventoryRepositoryInMemory.findById(new IngredientId("Random")));
    }

    @Test
    void givenIngredientInventoryIsInRepository_whenHasInventoryForIngredient_thenReturnTrue() {
        var chocolateInventory = givenChocolateInventoryInRepository();

        var hasInventory = ingredientInventoryRepositoryInMemory
                .hasInventoryForIngredient(chocolateInventory.getIngredientId());

        assertTrue(hasInventory);
    }

    @Test
    void givenIngredientInventoryIsNotInRepository_whenHasInventoryForIngredient_thenReturnFalse() {
        var hasInventory = ingredientInventoryRepositoryInMemory
                .hasInventoryForIngredient(new IngredientId("Random"));

        assertFalse(hasInventory);
    }

    @Test
    void givenIngredientInventoryIsInRepository_whenHasInventoryForIngredientWithName_thenReturnTrue() {
        var chocolateInventory = givenChocolateInventoryInRepository();

        var hasInventory = ingredientInventoryRepositoryInMemory
                .hasInventoryForIngredientWithName(chocolateInventory.getName());

        assertTrue(hasInventory);
    }

    @Test
    void givenIngredientInventoryIsNotInRepository_whenHasInventoryForIngredientWithName_thenReturnFalse() {
        var hasInventory = ingredientInventoryRepositoryInMemory
                .hasInventoryForIngredientWithName(SOME_INGREDIENT_NAME);

        assertFalse(hasInventory);
    }

    @Test
    void givenIngredientInventoryIsInRepository_whenUpdate_thenUpdateChocolateInventory() {
        var otherChocolateInventory = givenChocolateInventoryInRepository();
        otherChocolateInventory.addQuantity(SOME_OTHER_QUANTITY);

        ingredientInventoryRepositoryInMemory.update(otherChocolateInventory);

        var chocolateInventory = ingredientInventoryRepositoryInMemory
                .findById(otherChocolateInventory.getIngredientId());
        assertEquals(SOME_OTHER_QUANTITY + SOME_OTHER_QUANTITY, chocolateInventory.getQuantity());
    }

    @Test
    void givenIngredientInventoryIsNotInRepository_whenUpdate_thenThrowNonExistentIngredientInventoryException() {
        var nonExistentIngredient = new Ingredient("someName", new IngredientId("SomeName"), 10);

        assertThrows(NonExistentIngredientInventoryException.class,
                     () -> ingredientInventoryRepositoryInMemory.update(nonExistentIngredient));
    }

    Ingredient givenChocolateInventoryInRepository() {
        var ingredient = new Ingredient("Chocolate",
                                        new IngredientId("Chocolate"),
                                        SOME_OTHER_QUANTITY);
        ingredientInventoryRepositoryInMemory.add(ingredient);
        return ingredient;
    }
}
