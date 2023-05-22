package ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates;

import ca.ulaval.glo4002.cafe.domain.customer.Money;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

public class US implements Taxer {
    private final double countryTaxPercent = 0.00;
    private final State state;

    public US(State state) {
        this.state = state;
    }

    @Override
    public Money calculateTaxes(Money subtotal) {
        return subtotal.multiply(countryTaxPercent + state.taxPercent());
    }
}
