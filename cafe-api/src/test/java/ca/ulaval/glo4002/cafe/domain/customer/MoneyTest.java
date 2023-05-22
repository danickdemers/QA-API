package ca.ulaval.glo4002.cafe.domain.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {
    private static final double A_PRICE = 1.0;
    private static final double ANOTHER_PRICE = 2.0;

    @Test
    void givenTwoMoney_whenAddingThem_thenReturnTheirSum() {
        Money aMoney = new Money(A_PRICE);
        Money anotherMoney = new Money(ANOTHER_PRICE);

        Money addResult = aMoney.plus(anotherMoney);

        double expectedResult = A_PRICE + ANOTHER_PRICE;
        assertEquals(addResult.toDouble(), expectedResult);
    }

    @Test
    void givenMoneyWith0Decimal_whenToDoubleRoundUp_thenSameNumberReturned() {
        final float expectedValue = 1;
        Money money = new Money(expectedValue);

        double result = money.toDoubleRoundUp();

        assertEquals(expectedValue, result);
    }

    @Test
    void givenMoneyWith1Decimal_whenToDoubleRoundUp_thenSameNumberReturned() {
        final double expectedValue = 1.1;
        Money money = new Money(expectedValue);

        double result = money.toDoubleRoundUp();

        assertEquals(expectedValue, result);
    }

    @Test
    void givenMoneyWith2Decimal_whenToDoubleRoundUp_thenReturnSameNumber() {
        final double expectedValue = 1.11;
        Money money = new Money(expectedValue);

        double result = money.toDoubleRoundUp();

        assertEquals(expectedValue, result);
    }

    @Test
    void givenMoneyWith3Decimal_whenToDoubleRoundUp_thenReturnRoundedNumber() {
        final double value = 1.111;
        Money money = new Money(value);
        final double expectedValue = 1.12;

        double result = money.toDoubleRoundUp();

        assertEquals(expectedValue, result);
    }

    @Test
    void givenMoneyWith9Decimal_whenToDoubleRoundUp_thenReturnRoundedNumber() {
        final double value = 1.123456789;
        Money money = new Money(value);
        final double expectedValue = 1.13;

        double result = money.toDoubleRoundUp();

        assertEquals(expectedValue, result);
    }

    @Test
    void givenMoneyWith2DecimalAnd6NumbersBeforeDecimal_whenToDoubleRoundUp_thenReturnSameNumber() {
        final double value = 123456.11;
        Money money = new Money(value);
        final double expectedValue = 123456.11;

        double result = money.toDoubleRoundUp();

        assertEquals(expectedValue, result);
    }

    // The real value returned is 1234567.1, but it works because float can only keep 8 numbers and Money also only
    // keeps 8
    @Test
    void givenMoneyWith3DecimalAnd7NumbersBeforeDecimal_whenToDoubleRoundUp_thenReturnRoundedNumber() {
        final double value = 1234567.111;
        Money money = new Money(value);
        final double expectedValue = 1234567.12;

        double result = money.toDoubleRoundUp();

        assertEquals(expectedValue, result);
    }
}
