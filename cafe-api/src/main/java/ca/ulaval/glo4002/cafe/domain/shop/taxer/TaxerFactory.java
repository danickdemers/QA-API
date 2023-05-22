package ca.ulaval.glo4002.cafe.domain.shop.taxer;

import ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada.CA;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada.Province;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Chile.CL;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.None.None;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates.State;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates.US;

public class TaxerFactory {
    public Taxer create(Country country, Province province, State state) {
        if (country == Country.CA && province != Province.NONE) {
            return new CA(province);
        }
        if (country == Country.US && state != State.NONE) {
            return new US(state);
        }
        if (country == Country.CL) {
            return new CL();
        }
        if (country == Country.NONE) {
            return new None();
        }
        throw new InvalidCountryException();
    }
}
