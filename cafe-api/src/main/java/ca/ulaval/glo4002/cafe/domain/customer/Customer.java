package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.LinkedList;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.Billable;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

public class Customer implements Billable {
    private final CustomerId id;
    private final String name;
    private final List<Orderable> orders;
    private boolean isCheckedIn;
    private boolean isInReservation;

    public Customer(CustomerId id, String name, String reservationName) {
        this.id = id;
        this.name = name;
        this.orders = new LinkedList<>();
        isCheckedIn = true;
        isInReservation = reservationName != null && !reservationName.isEmpty();
    }

    public CustomerId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public List<String> getOrdersString() {
        return orders.stream().map(Orderable::toString).toList();
    }

    @Override
    public List<Orderable> getOrders() {
        return orders;
    }

    public void putOrders(List<? extends Orderable> orders) {
        this.orders.addAll(orders);
    }

    public void checkOut() {
        isCheckedIn = false;
    }

    @Override
    public boolean isCheckedOut() {
        return !isCheckedIn;
    }

    @Override
    public Bill createBill(Taxer taxer, GroupTipRate groupTipRate) {
        return new Bill(taxer, groupTipRate, this);
    }

    @Override
    public boolean isInReservation() {
        return isInReservation;
    }
}
