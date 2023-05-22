package ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.customer.Money;

import static org.junit.jupiter.api.Assertions.assertEquals;

class USTest {
    private static final Money A_SUBTOTAL = new Money(10f);
    private static final Money EXPECTED_AMOUNT_FOR_A_SUBTOTAL = new Money(0.625f);

    @Test
    void givenState_whenCalculateTaxes_thenReturnExpectedAmount() {
        var country = new US(State.TX);

        var tax = country.calculateTaxes(A_SUBTOTAL);

        assertEquals(EXPECTED_AMOUNT_FOR_A_SUBTOTAL, tax);
    }
}
