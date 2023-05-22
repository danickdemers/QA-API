package ca.ulaval.glo4002.cafe.domain.shop;

import ca.ulaval.glo4002.cafe.domain.customer.Money;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InvalidGroupTipRateException;

public class GroupTipRate {
    private final double defaultRate = 0;
    private final double groupTipRate;

    public GroupTipRate(double groupTipRate) {
        if (groupTipRate < 0 || groupTipRate > 100) {
            throw new InvalidGroupTipRateException();
        }

        this.groupTipRate = groupTipRate;
    }

    public GroupTipRate() {
        this.groupTipRate = defaultRate;
    }

    public Money calculateGroupTip(Money subtotal) {
        return subtotal.multiply(groupTipRate / 100.0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this == o) {
            return true;
        }

        GroupTipRate other = (GroupTipRate) o;

        return groupTipRate == other.groupTipRate;
    }

    @Override
    public int hashCode() {
        Double aDouble = groupTipRate;
        return aDouble.hashCode();
    }
}
