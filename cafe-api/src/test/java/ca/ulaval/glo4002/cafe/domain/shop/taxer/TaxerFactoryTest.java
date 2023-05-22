package ca.ulaval.glo4002.cafe.domain.shop.taxer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada.CA;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada.Province;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Chile.CL;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.None.None;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates.State;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates.US;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaxerFactoryTest {
    private TaxerFactory taxerFactory;

    @BeforeEach
    void initializeFactory() {
        taxerFactory = new TaxerFactory();
    }

    @Test
    void givenCanadaWithValidProvince_whenCreate_thenCreateCA() {
        var taxer = taxerFactory.create(Country.CA, Province.QC, State.NONE);

        assertEquals(taxer.getClass(), CA.class);
    }

    @Test
    void givenUnitedStatesWithValidState_whenCreate_thenCreateUS() {
        var taxer = taxerFactory.create(Country.US, Province.NONE, State.TX);

        assertEquals(taxer.getClass(), US.class);
    }

    @Test
    void givenChile_whenCreate_thenCreateCL() {
        var taxer = taxerFactory.create(Country.CL, Province.NONE, State.NONE);

        assertEquals(taxer.getClass(), CL.class);
    }

    @Test
    void givenNone_whenCreate_thenCreateNone() {
        var taxer = taxerFactory.create(Country.NONE, Province.NONE, State.NONE);

        assertEquals(taxer.getClass(), None.class);
    }

    @Test
    void givenCanadaWithoutProvince_whenCreate_thenTrowInvalidCountryException() {
        assertThrows(InvalidCountryException.class, () -> taxerFactory.create(Country.CA, Province.NONE, State.TX));
    }

    @Test
    void givenUnitedStateWithoutState_whenCreate_thenTrowInvalidCountryException() {
        assertThrows(InvalidCountryException.class,
                     () -> taxerFactory.create(Country.US, Province.QC, State.NONE));
    }

    @Test
    void givenChileWithState_whenCreate_thenReturnCL() {
        var taxer = taxerFactory.create(Country.CL, Province.QC, State.TX);

        assertEquals(taxer.getClass(), CL.class);
    }

    @Test
    void givenNoneWithState_whenCreate_thenReturnNone() {
        var taxer = taxerFactory.create(Country.NONE, Province.QC, State.TX);

        assertEquals(taxer.getClass(), None.class);
    }
}
