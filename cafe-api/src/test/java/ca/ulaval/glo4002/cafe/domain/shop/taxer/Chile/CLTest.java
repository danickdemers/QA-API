package ca.ulaval.glo4002.cafe.domain.shop.taxer.Chile;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.customer.Money;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CLTest {
    private static final Money A_SUBTOTAL = new Money(10.0);
    private static final Money EXPECTED_AMOUNT_FOR_A_SUBTOTAL = new Money(1.90);

    @Test
    void whenCalculateTaxes_thenReturnExpectedAmount() {
        var country = new CL();

        var tax = country.calculateTaxes(A_SUBTOTAL);

        assertEquals(EXPECTED_AMOUNT_FOR_A_SUBTOTAL, tax);
    }
}
