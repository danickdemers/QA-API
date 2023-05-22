package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.InvalidMenuOrderException;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.IngredientId;

public class Menu {
    private List<Coffee> coffees;

    public Menu() {
        coffees = new ArrayList<>();
    }

    public void addCoffee(String name, double price, Map<String, Integer> ingredientNameWithQuantity) {
        Map<IngredientId, Integer> ingredientWithQuantity = new HashMap<>();
        ingredientNameWithQuantity.forEach((ingredientName, quantity) -> {
            IngredientId ingredientId = new IngredientId(ingredientName);
            ingredientWithQuantity.put(ingredientId, quantity);
        });
        coffees.add(new Coffee(name, new Money(price), ingredientWithQuantity));
    }

    public Coffee getCoffee(String coffeeName) {
        for (var coffee: coffees) {
            if (coffee.getName().equals(coffeeName)) {
                return coffee;
            }
        }
        throw new InvalidMenuOrderException();
    }
}
