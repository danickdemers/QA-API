package ca.ulaval.glo4002.cafe.domain.shop.taxer.Chile;

import ca.ulaval.glo4002.cafe.domain.customer.Money;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

public class CL implements Taxer {
    private final double taxPercent = 0.19;

    @Override
    public Money calculateTaxes(Money subtotal) {
        return subtotal.multiply(taxPercent);
    }
}
