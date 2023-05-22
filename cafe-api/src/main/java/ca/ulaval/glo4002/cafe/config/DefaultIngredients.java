package ca.ulaval.glo4002.cafe.config;

public enum DefaultIngredients {
    ESPRESSO("Espresso"),
    WATER("Water"),
    CHOCOLATE("Chocolate"),
    MILK("Milk");

    private final String name;

    DefaultIngredients(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
