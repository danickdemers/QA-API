package ca.ulaval.glo4002.cafe.domain.inventory.ingredients;

import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.InsufficientIngredientsException;

public class Ingredient {
    private final String name;
    private IngredientId ingredientId;
    private int quantity;

    public Ingredient(String name, IngredientId ingredientId, int quantity) {
        this.name = name;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }
    public IngredientId getIngredientId() {
        return ingredientId;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    private boolean hasEnoughQuantity(Integer quantity) {
        return this.quantity >= quantity;
    }

    public void removeQuantity(Integer quantity) {
        if (!hasEnoughQuantity(quantity)) {
            throw new InsufficientIngredientsException();
        }

        this.quantity -= quantity;
    }
}
