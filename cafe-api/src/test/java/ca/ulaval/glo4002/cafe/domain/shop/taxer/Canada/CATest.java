package ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.customer.Money;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CATest {
    private static final Money A_SUBTOTAL = new Money(10);
    private static final Money EXPECTED_AMOUNT_FOR_A_SUBTOTAL = new Money(1.4975);

    @Test
    void givenProvince_whenCalculateTaxes_thenReturnExpectedAmount() {
        CA ca = new CA(Province.QC);

        var tax = ca.calculateTaxes(A_SUBTOTAL);

        assertEquals(EXPECTED_AMOUNT_FOR_A_SUBTOTAL, tax);
    }
}
