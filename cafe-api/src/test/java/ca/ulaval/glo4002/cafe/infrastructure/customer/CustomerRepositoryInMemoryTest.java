package ca.ulaval.glo4002.cafe.infrastructure.customer;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.DuplicateCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.InvalidCustomerIdException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerRepositoryInMemoryTest {
    private static final CustomerId CUSTOMER_ID = new CustomerId(UUID.randomUUID().toString());
    private static final CustomerId SOME_INVALID_CUSTOMER_ID = new CustomerId("hey ye yaae yae yaa");
    private static final String SOME_CUSTOMER_NAME = "hey ye yaae yaae";
    private static final String EMPTY_CUSTOMER_RESERVATION = "";
    private CustomerRepositoryInMemory customerRepositoryInMemory;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customerRepositoryInMemory = new CustomerRepositoryInMemory();
        customer = new Customer(CUSTOMER_ID, SOME_CUSTOMER_NAME, EMPTY_CUSTOMER_RESERVATION);
    }

    @Test
    void givenNonExistentCustomerWithId_whenFindById_thenThrowsInvalidCustomerIdException() {
        assertThrows(InvalidCustomerIdException.class,
                     () -> customerRepositoryInMemory.findById(SOME_INVALID_CUSTOMER_ID));
    }

    @Test
    void givenEmptyNewCustomerRepositoryInMemory_whenSave_thenCustomerIsAdded() {
        var expected = customer;

        customerRepositoryInMemory.save(customer);

        var actual = customerRepositoryInMemory.findById(CUSTOMER_ID);
        assertEquals(expected, actual);
    }

    @Test
    void givenRepositoryWithCustomer_whenUpdateSavedUser_thenDoesNotThrowException() {
        customerRepositoryInMemory.save(customer);

        assertDoesNotThrow(() -> customerRepositoryInMemory.update(customer));
    }

    @Test
    void givenRepositoryWithCustomer_whenExists_thenReturnsTrue() {
        customerRepositoryInMemory.save(customer);

        assertTrue(customerRepositoryInMemory.exists(CUSTOMER_ID));
    }

    @Test
    void givenEmptyRepository_whenExists_thenReturnsFalse() {
        assertFalse(customerRepositoryInMemory.exists(CUSTOMER_ID));
    }

    @Test
    void givenCustomerInRepositoryAndCustomerInParameterWithSameId_whenSave_thenThrowsDuplicateCustomerIdException() {
        customerRepositoryInMemory.save(customer);

        assertThrows(DuplicateCustomerIdException.class,
                     () -> customerRepositoryInMemory.save(customer));
    }

    @Test
    void givenEmptyNewCustomerRepositoryInMemory_whenUpdate_thenThrowsInvalidCustomerIdException() {
        assertThrows(InvalidCustomerIdException.class,
                     () -> customerRepositoryInMemory.update(customer));
    }

    @Test
    void givenRepositoryWithCustomer_whenReset_thenCustomerDoesNotExistAnymore() {
        customerRepositoryInMemory.save(customer);

        customerRepositoryInMemory.reset();

        assertFalse(customerRepositoryInMemory.exists(CUSTOMER_ID));
    }
}
