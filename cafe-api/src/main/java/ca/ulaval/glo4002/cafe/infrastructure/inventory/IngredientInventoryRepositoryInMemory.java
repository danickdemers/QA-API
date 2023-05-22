package ca.ulaval.glo4002.cafe.infrastructure.inventory;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.AlreadyPresentIngredientInventoryException;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.NonExistentIngredientInventoryException;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.IngredientId;

public class IngredientInventoryRepositoryInMemory implements IngredientInventoryRepository {
    Map<IngredientId, Ingredient> ingredients = new HashMap<>();

    @Override
    public void add(Ingredient ingredient) {
        if (hasInventoryForIngredient(ingredient.getIngredientId())) {
            throw new AlreadyPresentIngredientInventoryException();
        }
        ingredients.put(ingredient.getIngredientId(), ingredient);
    }

    @Override
    public Ingredient findById(IngredientId ingredientId) {
        if (!hasInventoryForIngredient(ingredientId)) {
            throw new NonExistentIngredientInventoryException();
        }
        return ingredients.get(ingredientId);
    }

    @Override
    public boolean hasInventoryForIngredient(IngredientId ingredientId) {
        return ingredients.containsKey(ingredientId);
    }

    @Override
    public boolean hasInventoryForIngredientWithName(String ingredientName) {
        return ingredients.containsKey(new IngredientId(ingredientName));
    }

    @Override
    public void update(Ingredient ingredient) {
        var inventoryId = ingredient.getIngredientId();
        if (!hasInventoryForIngredient(inventoryId)) {
            throw new NonExistentIngredientInventoryException();
        }
        ingredients.remove(inventoryId);
        add(ingredient);
    }

    @Override
    public void reset() {
        ingredients.clear();
    }
}
