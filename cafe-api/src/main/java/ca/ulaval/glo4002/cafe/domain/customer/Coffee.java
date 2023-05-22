package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.IngredientId;

public class Coffee implements Orderable {
    private final String name;
    private final Money price;
    private final Map<IngredientId, Integer> ingredients;

    public Coffee(String name, Money price, Map<IngredientId, Integer> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public Map<IngredientId, Integer> getIngredients() {
        return ingredients;
    }

    @Override
    public Money price() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
