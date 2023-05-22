package ca.ulaval.glo4002.cafe.domain.bill;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.customer.Orderable;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Taxer;

public interface Billable {
    List<Orderable> getOrders();
    List<String> getOrdersString();
    boolean isCheckedOut();
    boolean isInReservation();
    Bill createBill(Taxer taxer, GroupTipRate groupTipRate);
}
