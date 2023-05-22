package ca.ulaval.glo4002.cafe.domain.shop;

public enum Status {
    AVAILABLE("Available"),
    RESERVED("Reserved"),
    OCCUPIED("Occupied");
    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
