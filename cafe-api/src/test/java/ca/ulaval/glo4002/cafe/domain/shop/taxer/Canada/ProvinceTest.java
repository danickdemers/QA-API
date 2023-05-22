package ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProvinceTest {
    private static final String VALID_PROVINCE = "QC";
    private static final String INVALID_PROVINCE = "TX";

    @Test
    void givenValidProvince_whenFromString_thenReturnEnum() {
        var expected = Province.QC;

        var actual = Province.fromString(VALID_PROVINCE);

        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidProvince_whenFromString_thenReturnNONE() {
        var expected = Province.NONE;

        var actual = Province.fromString(INVALID_PROVINCE);

        assertEquals(expected, actual);
    }
}
