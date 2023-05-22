package ca.ulaval.glo4002.cafe.application.inventory.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IngredientsInventoryDto {
    @JsonProperty("Chocolate")
    public int chocolate;

    @JsonProperty("Espresso")
    public int espresso;

    @JsonProperty("Milk")
    public int milk;

    @JsonProperty("Water")
    public int water;

    public IngredientsInventoryDto(int chocolateQty, int espressoQty, int milkQty, int waterQty) {
        this.chocolate = chocolateQty;
        this.espresso = espressoQty;
        this.milk = milkQty;
        this.water = waterQty;
    }
}
