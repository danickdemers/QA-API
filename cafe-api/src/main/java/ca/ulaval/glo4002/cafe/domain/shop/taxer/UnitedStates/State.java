package ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates;

public enum State {
    AL("AL", 0.04),
    AZ("AZ", 0.056),
    CA("CA", 0.0725),
    FL("FL", 0.06),
    ME("ME", 0.055),
    NY("NY", 0.04),
    TX("TX", 0.0625),
    NONE("", 0);

    private final String name;
    private final double taxPercent;

    State(String name, double taxPercent) {
        this.name = name;
        this.taxPercent = taxPercent;
    }

    public static State fromString(String provinceName) {
        for (State item : values()) {
            if (item.name.equals(provinceName)) {
                return item;
            }
        }
        return NONE;
    }

    public double taxPercent() {
        return taxPercent;
    }
}
