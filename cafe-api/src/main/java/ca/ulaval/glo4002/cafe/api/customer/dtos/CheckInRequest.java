package ca.ulaval.glo4002.cafe.api.customer.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckInRequest {
    @JsonProperty("customer_id")
    public String customerId;
    @JsonProperty("customer_name")
    public String customerName;
    @JsonProperty("group_name")
    public String groupName;

    public CheckInRequest(String customerId, String customerName, String groupName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.groupName = groupName;
    }

    public CheckInRequest() { }

    public Boolean allParametersAreDefined() {
        return customerId != null && customerName != null;
    }
}
