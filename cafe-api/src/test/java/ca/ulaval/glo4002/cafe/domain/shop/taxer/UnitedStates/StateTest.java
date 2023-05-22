package ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StateTest {
    private static final String VALID_STATE = "TX";
    private static final String INVALID_STATE = "QC";

    @Test
    void givenValidState_whenFromString_thenReturnEnum() {
        var expected = State.TX;

        var actual = State.fromString(VALID_STATE);

        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidState_whenFromString_thenReturnNONE() {
        var expected = State.NONE;

        var actual = State.fromString(INVALID_STATE);

        assertEquals(expected, actual);
    }
}
