package ca.ulaval.glo4002.cafe.domain.shop.taxer;

import ca.ulaval.glo4002.cafe.domain.customer.Money;

public interface Taxer {
    Money calculateTaxes(Money subtotal);
}
