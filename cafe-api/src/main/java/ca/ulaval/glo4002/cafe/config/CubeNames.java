package ca.ulaval.glo4002.cafe.config;

public enum CubeNames {
    BLOOM("Bloom"),
    MERRYWEATHER("Merryweather"),
    TINKER_BELL("Tinker Bell"),
    WANDA("Wanda");

    private final String name;

    CubeNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
