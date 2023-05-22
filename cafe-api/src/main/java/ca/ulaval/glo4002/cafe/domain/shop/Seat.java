package ca.ulaval.glo4002.cafe.domain.shop;

import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

public class Seat implements CustomerInformation {
    private final Integer number;
    private CustomerId customerId;
    private String reservationName;

    public Seat(Integer number) {
        this.number = number;
    }

    public Integer getSeatNumber() {
        return number;
    }

    public void assign(CustomerId customerId, String reservationName) {
        this.customerId = customerId;
        this.reservationName = reservationName;
    }

    public void free() {
        customerId = null;
        reservationName = null;
    }

    public void reserve(String reservationName) {
        this.reservationName = reservationName;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public String getCustomerIdValue() {
        return customerId == null ? null : customerId.getCustomerIdValue();
    }

    public String getReservationName() {
        return reservationName;
    }

    public String getStatus() {
        Status status = Status.AVAILABLE;
        if (isOccupied()) {
            status = Status.OCCUPIED;
        } else if (isReserved()) {
            status = Status.RESERVED;
        }
        return status.getName();
    }

    public boolean isAvailable() {
        return !isReserved() && !isOccupied();
    }

    public boolean isReserved() {
        return Optional.ofNullable(reservationName).isPresent();
    }

    public boolean isOccupied() {
        return Optional.ofNullable(customerId).isPresent();
    }
}
