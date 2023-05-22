package ca.ulaval.glo4002.cafe.api.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.api.inventory.dtos.AddInventoryRequest;
import ca.ulaval.glo4002.cafe.application.inventory.IngredientInventoryService;
import ca.ulaval.glo4002.cafe.application.inventory.dtos.IngredientsInventoryDto;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {
    private static final int CHOCOLATE_QTY = 4;
    private static final int ESPRESSO_QTY = 7;
    private static final int MILK_QTY = 3;
    private static final int WATER_QTY = 10;

    @Mock
    private IngredientInventoryService ingredientInventoryService;
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        inventoryController = new InventoryController(ingredientInventoryService);
    }

    @Test
    void whenAddInventory_thenReturnOkStatus() {
        var addInventoryRequest = new AddInventoryRequest(CHOCOLATE_QTY, ESPRESSO_QTY, MILK_QTY, WATER_QTY);

        var response = inventoryController.addInventory(addInventoryRequest);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void whenAddInventory_thenDelegateAddingInventoryToIngredientsToService() {
        var addInventoryRequest = new AddInventoryRequest(CHOCOLATE_QTY, ESPRESSO_QTY, MILK_QTY, WATER_QTY);

        inventoryController.addInventory(addInventoryRequest);

        verify(ingredientInventoryService).addInventoryToIngredients(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void whenGetInventory_thenReturnOkStatus() {
        var response = inventoryController.getInventory();

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void whenGetInventory_thenReturnInventoryOfChocolateEspressoMilkAndWater() {
        var expectedDto = new IngredientsInventoryDto(CHOCOLATE_QTY, ESPRESSO_QTY, MILK_QTY, WATER_QTY);
        when(ingredientInventoryService.getIngredientsInventory()).thenReturn(expectedDto);

        var actualDto = (IngredientsInventoryDto) inventoryController.getInventory().getEntity();

        assertEquals(CHOCOLATE_QTY, actualDto.chocolate);
        assertEquals(ESPRESSO_QTY, actualDto.espresso);
        assertEquals(MILK_QTY, actualDto.milk);
        assertEquals(WATER_QTY, actualDto.water);
    }
}
