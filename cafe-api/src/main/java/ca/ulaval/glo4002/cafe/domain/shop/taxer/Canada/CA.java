package ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada;

import ca.ulaval.glo4002.cafe.domain.customer.Money;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

public class CA implements Taxer {
    private final double countryTaxPercent = 0.05;
    private final Province province;

    public CA(Province province) {
        this.province = province;
    }

    @Override
    public Money calculateTaxes(Money subtotal) {
        return subtotal.multiply(countryTaxPercent + province.taxPercent());
    }
}
