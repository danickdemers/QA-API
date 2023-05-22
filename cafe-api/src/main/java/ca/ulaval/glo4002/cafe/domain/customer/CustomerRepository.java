package ca.ulaval.glo4002.cafe.domain.customer;

public interface CustomerRepository {
    boolean exists(CustomerId customerId);
    Customer findById(CustomerId customerId);
    void save(Customer customer);

    void update(Customer customer);

    void reset();
}
