package ca.ulaval.glo4002.cafe.domain.shop.taxer;

public enum Country {
    CA("CA"),
    US("US"),
    CL("CL"),
    NONE("None");

    private final String name;

    Country(String name) {
        this.name = name;
    }

    public static Country fromString(String countryName) {
        for (Country item : values()) {
            if (item.name.equals(countryName)) {
                return item;
            }
        }
        throw new InvalidCountryException();
    }
}
