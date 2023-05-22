package ca.ulaval.glo4002.cafe.domain.shop.taxer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryTest {
    private static final String VALID_COUNTRY = "CA";
    private static final String INVALID_COUNTRY = "CH";
    private static final String EMPTY_COUNTRY = "";

    @Test
    void givenValidCountry_whenFromString_thenReturnEnum() {
        var expected = Country.CA;

        var actual = Country.fromString(VALID_COUNTRY);

        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidCountry_whenFromString_thenThrowsInvalidCountryException() {
        assertThrows(InvalidCountryException.class,
                     () -> Country.fromString(INVALID_COUNTRY));
    }

    @Test
    void givenEmptyCountry_whenFromString_thenThrowsInvalidCountryException() {
        assertThrows(InvalidCountryException.class,
                     () -> Country.fromString(EMPTY_COUNTRY));
    }
}
