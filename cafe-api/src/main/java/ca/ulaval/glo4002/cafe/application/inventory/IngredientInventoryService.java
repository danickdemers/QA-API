package ca.ulaval.glo4002.cafe.application.inventory;

import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.cafe.application.inventory.dtos.IngredientsInventoryDto;
import ca.ulaval.glo4002.cafe.config.DefaultIngredients;
import ca.ulaval.glo4002.cafe.domain.customer.Coffee;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.IngredientId;

public class IngredientInventoryService {
    private final IngredientInventoryRepository ingredientInventoryRepository;

    public IngredientInventoryService(IngredientInventoryRepository ingredientInventoryRepository) {
        this.ingredientInventoryRepository = ingredientInventoryRepository;
    }

    public void addInventoryToIngredients(int chocolate, int espresso, int milk, int water) {
        addInventoryToIngredient(DefaultIngredients.CHOCOLATE.getName(), chocolate);
        addInventoryToIngredient(DefaultIngredients.ESPRESSO.getName(), espresso);
        addInventoryToIngredient(DefaultIngredients.MILK.getName(), milk);
        addInventoryToIngredient(DefaultIngredients.WATER.getName(), water);
    }

    public IngredientsInventoryDto getIngredientsInventory() {
        var chocolateQty = getInventoryForIngredient(new IngredientId(DefaultIngredients.CHOCOLATE.getName()));
        var espressoQty = getInventoryForIngredient(new IngredientId(DefaultIngredients.ESPRESSO.getName()));
        var milkQty = getInventoryForIngredient(new IngredientId(DefaultIngredients.MILK.getName()));
        var waterQty = getInventoryForIngredient(new IngredientId(DefaultIngredients.WATER.getName()));
        return new IngredientsInventoryDto(chocolateQty, espressoQty, milkQty, waterQty);
    }

    public void removeInventoryForOrder(List<Coffee> orders) {
        for (var order : orders) {
            updateOrderIngredientsInventory(order.getIngredients());
        }
    }

    private void updateOrderIngredientsInventory(Map<IngredientId, Integer> ingredients) {
        ingredients.forEach((ingredientId, quantity) -> {
            var ingredient = ingredientInventoryRepository.findById(ingredientId);
            ingredient.removeQuantity(quantity);
            ingredientInventoryRepository.update(ingredient);
        });
    }

    private int getInventoryForIngredient(IngredientId ingredientId) {
        var ingredientFromRepo = ingredientInventoryRepository.findById(ingredientId);
        return ingredientFromRepo.getQuantity();
    }

    private void addInventoryToIngredient(String ingredientName, int quantity) {
        var ingredientId = new IngredientId(ingredientName);
        if (ingredientInventoryRepository.hasInventoryForIngredient(ingredientId)) {
            updateIngredientInventory(ingredientId, quantity);
        } else {
            createIngredientInventory(ingredientName, quantity);
        }
    }

    private void updateIngredientInventory(IngredientId ingredientId, int quantity) {
        var ingredient = ingredientInventoryRepository.findById(ingredientId);
        ingredient.addQuantity(quantity);
        ingredientInventoryRepository.update(ingredient);
    }

    private void createIngredientInventory(String ingredientName, int quantity) {
        var ingredient = new Ingredient(ingredientName, new IngredientId(ingredientName), quantity);
        ingredientInventoryRepository.add(ingredient);
    }
}
