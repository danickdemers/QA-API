package ca.ulaval.glo4002.cafe.domain.bill;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.domain.customer.Money;
import ca.ulaval.glo4002.cafe.domain.customer.Orderable;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillTest {
    private static final String SOME_ORDER_NAME = "someOrderName";
    private static final String SOME_OTHER_ORDER_NAME = "someOtherOrderName";
    private static final double SOME_PRICE = 2;
    private static final GroupTipRate GROUP_TIP_RATE_0 = new GroupTipRate(0);
    private static final Money SOME_PRICE_MONEY = new Money(SOME_PRICE);
    private static final GroupTipRate NON_0_GROUP_TIP_RATE = new GroupTipRate(20);
    private final List<Orderable> orderables = new ArrayList<>();
    private final List<String> orderablesString = new ArrayList<>();
    @Mock
    private Taxer taxer;
    @Mock
    private Billable billable;

    @BeforeEach
    void setUp() {
        orderables.clear();
        orderablesString.clear();
    }

    @Test
    void givenBillableNotCheckedOut_whenCreateBill_thenNoBillExceptionThrown() {
        when(billable.isCheckedOut()).thenReturn(false);

        assertThrows(NoBillException.class, () -> new Bill(taxer, GROUP_TIP_RATE_0, billable));
    }

    @Test
    void givenAnOrder_whenCreateBill_thenOrderNameParsed() {
        setUpBeforeEachExceptForOneTest();
        createOrderableAndAddToList(SOME_ORDER_NAME, SOME_PRICE);

        var result = new Bill(taxer, GROUP_TIP_RATE_0, billable);

        assertEquals(1, result.getOrders().size());
        assertEquals(SOME_ORDER_NAME, result.getOrders().get(0));
    }

    @Test
    void givenMultipleOrders_whenCreateBill_thenAllOrderNamesParsed() {
        setUpBeforeEachExceptForOneTest();
        createOrderableAndAddToList(SOME_ORDER_NAME, SOME_PRICE);
        createOrderableAndAddToList(SOME_OTHER_ORDER_NAME, SOME_PRICE);

        var result = new Bill(taxer, GROUP_TIP_RATE_0, billable);

        assertEquals(2, result.getOrders().size());
        assertTrue(result.getOrders().contains(SOME_ORDER_NAME));
        assertTrue(result.getOrders().contains(SOME_OTHER_ORDER_NAME));
    }

    @Test
    void givenOneOrderPrice_whenCreateBill_thenSubtotalIsOrderPrice() {
        setUpBeforeEachExceptForOneTest();
        double orderPrice = 12;
        createOrderableAndAddToList(SOME_ORDER_NAME, orderPrice);

        var result = new Bill(taxer, GROUP_TIP_RATE_0, billable);

        assertEquals(orderPrice, result.getSubtotal().toDouble());
    }

    @Test
    void givenOrderPrices_whenCreateBill_thenSubtotalIsAdditionOfPrices() {
        setUpBeforeEachExceptForOneTest();
        final double price1 = 12;
        final double price2 = 7;
        createOrderableAndAddToList(SOME_ORDER_NAME, price1);
        createOrderableAndAddToList(SOME_ORDER_NAME, price2);
        final double expectedFloatPrice = price1 + price2;

        var result = new Bill(taxer, GROUP_TIP_RATE_0, billable);

        assertEquals(expectedFloatPrice, result.getSubtotal().toDouble());
    }

    @Test
    void givenSomeSubtotalAndTaxes_whenCreateBill_thenTaxesCalculated() {
        setUpBeforeEachExceptForOneTest();
        createOrderableAndAddToList(SOME_ORDER_NAME, SOME_PRICE);
        final Money expectedTaxPrice = new Money(1);
        when(taxer.calculateTaxes(new Money(SOME_PRICE))).thenReturn(expectedTaxPrice);

        var result = new Bill(taxer, GROUP_TIP_RATE_0, billable);

        assertEquals(expectedTaxPrice, result.getTax());
    }

    @Test
    void givenSubtotalAndTaxes_whenCreateBill_thenTotalIsAdditionOfSubtotalAndTaxes() {
        setUpBeforeEachExceptForOneTest();
        final double subtotal = 17;
        createOrderableAndAddToList(SOME_ORDER_NAME, subtotal);
        final double taxPrice = 0.15;
        when(taxer.calculateTaxes(new Money(subtotal))).thenReturn(new Money(taxPrice));
        final double expectedTotal = subtotal + taxPrice;

        var result = new Bill(taxer, GROUP_TIP_RATE_0, billable);

        assertEquals(expectedTotal, result.getTotal().toDouble());
    }

    @Test
    void givenSubtotalAndTaxesAndTipAndNotInReservation_whenCreateBill_thenTotalIsAdditionOfSubtotalAndTaxesAndNoTip() {
        setUpBeforeEachExceptForOneTest();
        final double subtotal = 17;
        createOrderableAndAddToList(SOME_ORDER_NAME, subtotal);
        final double taxPrice = 0.15;
        when(taxer.calculateTaxes(new Money(subtotal))).thenReturn(new Money(taxPrice));
        final double expectedTotal = subtotal + taxPrice; // expected no tip

        var result = new Bill(taxer, NON_0_GROUP_TIP_RATE, billable);

        assertEquals(expectedTotal, result.getTotal().toDouble());
    }

    @Test
    void givenSubtotalAndTaxesAndTipAndIsInReservation_whenCreateBill_thenTotalIsAdditionOfSubtotalAndTaxesAndTip() {
        setUpBeforeEachExceptForOneTest();
        final double subtotal = 17;
        createOrderableAndAddToList(SOME_ORDER_NAME, subtotal);
        final double taxPrice = 0.15;
        final double tip = NON_0_GROUP_TIP_RATE.calculateGroupTip(new Money(subtotal)).toDouble();
        when(taxer.calculateTaxes(new Money(subtotal))).thenReturn(new Money(taxPrice));
        when(billable.isInReservation()).thenReturn(true);
        final double expectedTotal = new Money(subtotal + taxPrice + tip).toDouble(); // fix precision loss

        var result = new Bill(taxer, NON_0_GROUP_TIP_RATE, billable);

        assertEquals(expectedTotal, result.getTotal().toDouble());
    }

    // The test "givenBillableNotCheckedOut_whenCreateBill_thenNoBillExceptionThrown" fails if this is
    // in the @BeforeEach method because it sets stubs that it doesn't need...
    private void setUpBeforeEachExceptForOneTest() {
        when(billable.getOrders()).thenReturn(orderables);
        when(billable.getOrdersString()).thenReturn(orderablesString);
        when(billable.isCheckedOut()).thenReturn(true);
        when(taxer.calculateTaxes(any(Money.class))).thenReturn(SOME_PRICE_MONEY);
    }

    private void createOrderableAndAddToList(String name, double price) {
        Orderable order = new OrderDouble(name, price);
        orderables.add(order);
        orderablesString.add(order.toString());
    }

    private class OrderDouble implements Orderable {
        private final String name;
        private final Money price;

        OrderDouble(String name, double price) {
            this.name = name;
            this.price = new Money(price);
        }

        public String toString() {
            return name;
        }

        public Money price() {
            return price;
        }
    }
}
