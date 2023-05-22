package ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada;

public enum Province {
    AB("AB", 0.00),
    BC("BC", 0.07),
    MB("MB", 0.07),
    NB("NB", 0.10),
    NL("NL", 0.10),
    NS("NS", 0.10),
    NT("NT", 0.00),
    NU("NU", 0.00),
    ON("ON", 0.08),
    PE("PE", 0.10),
    QC("QC", 0.09975),
    SK("SK", 0.06),
    YT("YT", 0.00),
    NONE("", 0.00);

    private final String name;
    private final double taxPercent;

    Province(String name, double taxPercent) {
        this.name = name;
        this.taxPercent = taxPercent;
    }

    public static Province fromString(String provinceName) {
        for (Province item : values()) {
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
