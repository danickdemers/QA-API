package ca.ulaval.glo4002.cafe.domain.customer;

public class CustomerFactory {
    public Customer create(CustomerId id, String name, String reservationName) {
        return new Customer(id, name, reservationName);
    }
}
