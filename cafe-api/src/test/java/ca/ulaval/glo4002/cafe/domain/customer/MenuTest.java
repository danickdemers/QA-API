package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.InvalidMenuOrderException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuTest {
    private static final String VALID_NAME = "Flat White";
    private static final double VALID_PRICE = 0.0;
    private static final Map VALID_INGREDIENTS = new HashMap();
    Menu menu;

    @BeforeEach
    void createMenu() {
        menu = new Menu();
    }
    @Test
    void givenValidCoffeeWasAdded_whenGetCoffee_thenReturnsValidCoffee() {
        menu.addCoffee(VALID_NAME, VALID_PRICE, VALID_INGREDIENTS);

        var coffee = menu.getCoffee(VALID_NAME);

        assertEquals(coffee.getName(), VALID_NAME);
        assertTrue(coffee.getIngredients().isEmpty());
    }

    @Test
    void givenSomeInvalidMenuItem_whenGetCoffee_thenThrowsInvalidMenuOrderException() {
        assertThrows(InvalidMenuOrderException.class,
                     () -> menu.getCoffee("Not created coffee"));
    }
}
