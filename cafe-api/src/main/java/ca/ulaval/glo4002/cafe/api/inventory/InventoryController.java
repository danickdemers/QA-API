package ca.ulaval.glo4002.cafe.api.inventory;

import ca.ulaval.glo4002.cafe.api.inventory.dtos.AddInventoryRequest;
import ca.ulaval.glo4002.cafe.api.shared.exceptions.MissingParameterException;
import ca.ulaval.glo4002.cafe.application.inventory.IngredientInventoryService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/inventory")
public class InventoryController {
    private final IngredientInventoryService ingredientInventoryService;

    public InventoryController(IngredientInventoryService ingredientInventoryService) {
        this.ingredientInventoryService = ingredientInventoryService;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInventory(AddInventoryRequest addInventoryRequest) {
        if (!addInventoryRequest.allParametersAreDefined()) {
            throw new MissingParameterException();
        }
        ingredientInventoryService.addInventoryToIngredients(addInventoryRequest.chocolate,
                                                             addInventoryRequest.espresso,
                                                             addInventoryRequest.milk,
                                                             addInventoryRequest.water);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventory() {
        var ingredientsInventory = ingredientInventoryService.getIngredientsInventory();
        return Response.status(Response.Status.OK).entity(ingredientsInventory).build();
    }
}
