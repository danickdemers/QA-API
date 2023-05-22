package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.customer.dtos.CustomerBillDto;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;

public class CustomerBillAssembler {
    CustomerBillDto billToCustomerBillDto(Bill bill) {
        return new CustomerBillDto(bill.getOrders(),
                                   bill.getSubtotalForDisplay(),
                                   bill.getTaxForDisplay(),
                                   bill.getTipForDisplay(),
                                   bill.getTotalForDisplay());
    }
}
