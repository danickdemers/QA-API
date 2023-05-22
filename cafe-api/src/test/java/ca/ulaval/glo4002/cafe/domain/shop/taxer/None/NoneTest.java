package ca.ulaval.glo4002.cafe.domain.shop.taxer.None;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.customer.Money;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoneTest {
    private static final Money A_SUBTOTAL = new Money(10f);
    private static final Money EXPECTED_AMOUNT_FOR_A_SUBTOTAL = new Money(0f);

    @Test
    void givenProvince_whenCalculateTaxes_thenReturnExpectedAmount() {
        var none = new None();

        var tax = none.calculateTaxes(A_SUBTOTAL);

        assertEquals(EXPECTED_AMOUNT_FOR_A_SUBTOTAL, tax);
    }
}
