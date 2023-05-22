package ca.ulaval.glo4002.cafe.domain.shop;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InvalidGroupTipRateException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GroupTipRateTest {
    private static final int VALID_GROUP_TIP_RATE = 15;
    private static final int TOO_HIGH_GROUP_TIP_RATE = 101;
    private static final int TOO_LOW_GROUP_TIP_RATE = -1;

    @Test
    void givenTooHighGroupTipRate_whenCreatingGroupTipRate_thenThrowInvalidGroupTipRateException() {
        assertThrows(InvalidGroupTipRateException.class, () -> new GroupTipRate(TOO_HIGH_GROUP_TIP_RATE));
    }

    @Test
    void givenUnderGroupTipRate_whenGroupTipRate_thenThrowInvalidGroupTipRateException() {
        assertThrows(InvalidGroupTipRateException.class, () -> new GroupTipRate(TOO_LOW_GROUP_TIP_RATE));
    }
}
