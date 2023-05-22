package ca.ulaval.glo4002.cafe.api.menu.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.cafe.api.inventory.dtos.AddInventoryRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuRequest {
    @JsonProperty("name")
    public String name;
    @JsonProperty("ingredients")
    public AddInventoryRequest ingredients;
    @JsonProperty("cost")
    public Double cost;

    public MenuRequest(String name, AddInventoryRequest ingredients, Double cost) {
        this.name = name;
        this.ingredients = ingredients;
        this.cost = cost;
    }

    public MenuRequest() {
    }

    public Boolean allParametersAreDefined() {
        return name != null && ingredients.allParametersAreDefined() && cost != null;
    }
}
