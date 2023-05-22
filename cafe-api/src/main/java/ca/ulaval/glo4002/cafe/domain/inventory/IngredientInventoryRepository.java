package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.IngredientId;

public interface IngredientInventoryRepository {
    void add(Ingredient ingredient);

    Ingredient findById(IngredientId ingredientId);

    boolean hasInventoryForIngredient(IngredientId ingredientId);

    boolean hasInventoryForIngredientWithName(String ingredientName);

    void update(Ingredient ingredient);

    void reset();
}
