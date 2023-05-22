package ca.ulaval.glo4002.cafe.api.customer.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {
    @JsonProperty("orders")
    public List<String> orders;

    public OrderRequest(List<String> orders) {
        this.orders = orders;
    }

    public OrderRequest() { }
}
