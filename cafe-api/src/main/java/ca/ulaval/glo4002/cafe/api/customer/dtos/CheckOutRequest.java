package ca.ulaval.glo4002.cafe.api.customer.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckOutRequest {
    @JsonProperty("customer_id")
    public String customerId;

    public CheckOutRequest(String customerId) {
        this.customerId = customerId;
    }

    public CheckOutRequest() { }

    public Boolean allParametersAreDefined() {
        return customerId != null;
    }
}
