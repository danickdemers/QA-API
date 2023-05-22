package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.Objects;

public class CustomerId {
    private String customerIdValue;

    public CustomerId(String customerId) {
        this.customerIdValue = customerId;
    }

    public String getCustomerIdValue() {
        return customerIdValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CustomerId that)) {
            return false;
        }

        return Objects.equals(customerIdValue, that.customerIdValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerIdValue);
    }
}
