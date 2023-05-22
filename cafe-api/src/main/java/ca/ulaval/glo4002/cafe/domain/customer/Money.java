package ca.ulaval.glo4002.cafe.domain.customer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Money {
    private static final MathContext MATH_CONTEXT = new MathContext(10, RoundingMode.HALF_UP);
    private final BigDecimal value;

    public Money(double price) {
        this.value = new BigDecimal(price, MATH_CONTEXT);
    }

    public Money(BigDecimal price) {
        this.value = new BigDecimal(price.toString(), MATH_CONTEXT);
    }

    public double toDouble() {
        return value.doubleValue();
    }

    public double toDoubleRoundUp() {
        return value.setScale(2, RoundingMode.CEILING).doubleValue();
    }

    public Money plus(Money other) {
        return new Money(value.add(other.value));
    }

    public Money multiply(double otherNumber) {
        return new Money(value.multiply(new BigDecimal(otherNumber, MATH_CONTEXT)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Money money = (Money) o;

        return value.compareTo(money.value) == 0;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
