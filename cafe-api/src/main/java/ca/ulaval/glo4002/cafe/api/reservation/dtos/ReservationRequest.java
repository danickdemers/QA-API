package ca.ulaval.glo4002.cafe.api.reservation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationRequest {
    @JsonProperty("group_name")
    public String groupName;
    @JsonProperty("group_size")
    public Integer groupSize;

    public ReservationRequest() {

    }

    public ReservationRequest(String groupName, Integer groupSize) {
        this.groupName = groupName;
        this.groupSize = groupSize;
    }

    public Boolean allParametersAreDefined() {
        return groupName != null && groupSize != null;
    }
}
