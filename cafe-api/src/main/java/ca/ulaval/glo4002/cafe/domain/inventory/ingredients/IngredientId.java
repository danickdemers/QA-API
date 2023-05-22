package ca.ulaval.glo4002.cafe.domain.inventory.ingredients;

public class IngredientId {
    private final String ingredientName;

    public IngredientId(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String toString() {
        return ingredientName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof IngredientId)) {
            return false;
        }

        return ingredientName == ((IngredientId) o).ingredientName;
    }

    @Override
    public int hashCode() {
        return ingredientName.hashCode();
    }
}
