package ca.ulaval.glo4002.cafe.config;

import java.util.Map;

public enum DefaultCoffees {
    AMERICANO("Americano", 2.25, Map.of("Espresso", 50, "Water", 50)),
    CAPPUCCINO("Cappuccino", 3.29, Map.of("Espresso", 50, "Water", 40, "Milk", 10)),
    DARKROAST("Dark Roast", 2.10, Map.of("Espresso", 40, "Water", 40, "Chocolate", 10,
                                         "Milk", 10)),
    ESPRESSO("Espresso", 2.95, Map.of("Espresso", 60)),
    FLATWHITE("Flat White", 3.75, Map.of("Espresso", 50, "Milk", 50)),
    LATTE("Latte", 2.95, Map.of("Espresso", 50, "Milk", 50)),
    MACCHIATO("Macchiato", 4.75, Map.of("Espresso", 80, "Milk", 20)),
    MOCHA("Mocha", 4.15, Map.of("Espresso", 50, "Milk", 40, "Chocolate", 10));

    private final String name;
    private final double price;
    private final Map<String, Integer> ingredients;

    DefaultCoffees(String name, double price, Map<String, Integer> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }
}
