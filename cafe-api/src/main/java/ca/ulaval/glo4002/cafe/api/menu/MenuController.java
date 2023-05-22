package ca.ulaval.glo4002.cafe.api.menu;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.cafe.api.inventory.dtos.AddInventoryRequest;
import ca.ulaval.glo4002.cafe.api.menu.dtos.MenuRequest;
import ca.ulaval.glo4002.cafe.application.Menu.MenuService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Path("menu")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postMenu(MenuRequest request) {

        menuService.addToMenu(request.name, request.cost, getIngredientMap(request.ingredients));

        return Response.status(Response.Status.OK).build();
    }

    private Map<String, Integer> getIngredientMap(AddInventoryRequest addInventoryRequest) {
        Map<String, Integer> ingredientMap = new HashMap<>();

        ingredientMap.put("Chocolate", addInventoryRequest.chocolate);
        ingredientMap.put("Espresso", addInventoryRequest.espresso);
        ingredientMap.put("Milk", addInventoryRequest.milk);
        ingredientMap.put("Water", addInventoryRequest.water);

        return  ingredientMap;
    }
}
