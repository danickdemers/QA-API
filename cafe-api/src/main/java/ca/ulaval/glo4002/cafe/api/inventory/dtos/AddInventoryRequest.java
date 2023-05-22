package ca.ulaval.glo4002.cafe.api.inventory.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddInventoryRequest {
    @JsonProperty("Chocolate")
    public Integer chocolate;

    @JsonProperty("Espresso")
    public Integer espresso;

    @JsonProperty("Milk")
    public Integer milk;

    @JsonProperty("Water")
    public Integer water;

    public AddInventoryRequest(Integer chocolate, Integer espresso, Integer milk, Integer water) {
        this.chocolate = chocolate;
        this.espresso = espresso;
        this.milk = milk;
        this.water = water;
    }
    public AddInventoryRequest() { }

    public Boolean allParametersAreDefined() {
        return chocolate != null && espresso != null && milk != null && water != null && chocolate >= 0
                && espresso >= 0 && milk >= 0 && water >= 0 ;
    }
}
