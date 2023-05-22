package ca.ulaval.glo4002.cafe.domain.bill;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.customer.Money;
import ca.ulaval.glo4002.cafe.domain.customer.Orderable;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

public class Bill {
    private final Taxer taxer;
    private final GroupTipRate groupTipRate;
    private final List<String> orders;
    private final Money subtotal;
    private final Money tax;
    private final Money tip;
    private final Money total;

    public Bill(Taxer taxer, GroupTipRate groupTipRate, Billable billable) {
        if (!billable.isCheckedOut()) {
            throw new NoBillException();
        }

        this.taxer = taxer;
        this.groupTipRate = groupTipRate;
        orders = billable.getOrdersString();
        subtotal = calculateSubtotal(billable.getOrders());
        tip = calculateTip(subtotal, billable);
        tax = calculateTax(subtotal);
        total = calculateTotal(subtotal, tax, tip);
    }

    public List<String> getOrders() {
        return orders;
    }

    public Money getSubtotal() {
        return subtotal;
    }

    public Money getTax() {
        return tax;
    }

    public Money getTip() {
        return tip;
    }

    public Money getTotal() {
        return total;
    }

    public double getSubtotalForDisplay() {
        return subtotal.toDoubleRoundUp();
    }

    public double getTaxForDisplay() {
        return tax.toDoubleRoundUp();
    }

    public double getTipForDisplay() {
        return tip.toDoubleRoundUp();
    }

    public double getTotalForDisplay() {
        return total.toDoubleRoundUp();
    }

    private Money calculateSubtotal(List<Orderable> orders) {
        Money subtotal = new Money(0);
        for (Orderable order : orders) {
            subtotal = subtotal.plus(order.price());
        }
        return subtotal;
    }

    private Money calculateTax(Money subtotal) {
        return taxer.calculateTaxes(subtotal);
    }

    private Money calculateTip(Money subtotal, Billable billable) {
        if (billable.isInReservation()) {
            return groupTipRate.calculateGroupTip(subtotal);
        }
        return new Money(0);
    }

    private Money calculateTotal(Money subtotal, Money tax, Money tip) {
        return subtotal.plus(tax).plus(tip);
    }
}
