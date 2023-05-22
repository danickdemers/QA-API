package ca.ulaval.glo4002.cafe.application.customer.dtos;

import java.util.List;

public record CustomerBillDto(List<String> orders, double subtotal, double taxes, double tip, double total) {
}
