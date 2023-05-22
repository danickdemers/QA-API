package ca.ulaval.glo4002.cafe.domain.shop.strategy;

public enum ReservationType {
    DEFAULT("Default"),
    FULLCUBE("Full Cubes"),
    NOLONER("No Loners");

    private final String name;

    ReservationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
