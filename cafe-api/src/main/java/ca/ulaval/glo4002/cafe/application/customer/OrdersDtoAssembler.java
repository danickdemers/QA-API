package ca.ulaval.glo4002.cafe.application.customer;

import java.util.List;

import ca.ulaval.glo4002.cafe.application.customer.dtos.OrdersDto;

public class OrdersDtoAssembler {
    public OrdersDto ordersToOrdersDto(List<String> orders) {
        return new OrdersDto(orders);
    }
}
