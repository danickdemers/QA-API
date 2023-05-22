package ca.ulaval.glo4002.cafe.infrastructure.customer;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.DuplicateCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.InvalidCustomerIdException;

public class CustomerRepositoryInMemory implements CustomerRepository {

    Map<CustomerId, Customer> customers = new HashMap<>();

    @Override
    public Customer findById(CustomerId customerId) {
        if (!customers.containsKey(customerId)) {
            throw new InvalidCustomerIdException();
        }

        return customers.get(customerId);
    }

    @Override
    public boolean exists(CustomerId customerId) {
        return customers.containsKey(customerId);
    }

    @Override
    public void save(Customer customer) {
        if (customers.containsKey(customer.getId())) {
            throw new DuplicateCustomerIdException();
        }

        customers.put(customer.getId(), customer);
    }

    @Override
    public void update(Customer customer) {
        if (!customers.containsKey(customer.getId())) {
            throw new InvalidCustomerIdException();
        }
        customers.replace(customer.getId(), customer);
    }

    @Override
    public void reset() {
        customers = new HashMap<>();
    }
}
