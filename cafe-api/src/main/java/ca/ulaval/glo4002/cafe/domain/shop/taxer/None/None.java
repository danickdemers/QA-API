package ca.ulaval.glo4002.cafe.domain.shop.taxer.None;

import ca.ulaval.glo4002.cafe.domain.customer.Money;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

public class None implements Taxer {
    @Override
    public Money calculateTaxes(Money subtotal) {
        return new Money(0.0);
    }
}
