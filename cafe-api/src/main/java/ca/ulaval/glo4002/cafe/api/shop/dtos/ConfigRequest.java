package ca.ulaval.glo4002.cafe.api.shop.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigRequest {
    @JsonProperty("group_reservation_method")
    public String groupReservationMethod;
    @JsonProperty("organization_name")
    public String organizationName;
    @JsonProperty("cube_size")
    public Integer cubeSize;
    @JsonProperty("country")
    public String country;
    @JsonProperty("province")
    public String province;
    @JsonProperty("state")
    public String state;
    @JsonProperty("group_tip_rate")
    public Double groupTipRate;

    public ConfigRequest() {
    }

    public ConfigRequest(String groupReservationMethod, String organizationName, Integer cubeSize, String country,
                         String province, String state, double groupTipRate) {
        this.groupReservationMethod = groupReservationMethod;
        this.organizationName = organizationName;
        this.cubeSize = cubeSize;
        this.country = country;
        this.province = province;
        this.state = state;
        this.groupTipRate = groupTipRate;
    }

    public Boolean allParametersAreDefined() {
        return groupReservationMethod != null && organizationName != null &&
                cubeSize != null && country != null && province != null && state != null && groupTipRate != null;
    }
}
