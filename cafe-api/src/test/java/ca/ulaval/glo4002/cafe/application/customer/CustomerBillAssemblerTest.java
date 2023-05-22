package ca.ulaval.glo4002.cafe.application.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.Billable;
import ca.ulaval.glo4002.cafe.domain.customer.Money;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerBillAssemblerTest {
    private static final Money SOME_PRICE_MONEY = new Money(1);
    private static final GroupTipRate SOME_GROUPE_TIP_RATE = new GroupTipRate(0);
    @Mock
    private Taxer taxer;
    @Mock
    private Billable billable;
    private CustomerBillAssembler customerBillAssembler;

    @BeforeEach
    void setUp() {
        customerBillAssembler = new CustomerBillAssembler();
        billableConstructorMinimumSetup();
    }

    @Test
    void givenBill_whenBillToCustomerBillDto_thenConvertedToDto() {

        Bill bill = new Bill(taxer, SOME_GROUPE_TIP_RATE, billable);

        var result = customerBillAssembler.billToCustomerBillDto(bill);

        assertEquals(bill.getOrders(), result.orders());
        assertEquals(bill.getSubtotal().toDouble(), result.subtotal());
        assertEquals(bill.getTax().toDouble(), result.taxes());
        assertEquals(bill.getTip().toDouble(), result.tip());
        assertEquals(bill.getTotal().toDouble(), result.total());
    }

    private void billableConstructorMinimumSetup() {
        when(billable.isCheckedOut()).thenReturn(true);
        when(taxer.calculateTaxes(any(Money.class))).thenReturn(SOME_PRICE_MONEY);
    }
}
