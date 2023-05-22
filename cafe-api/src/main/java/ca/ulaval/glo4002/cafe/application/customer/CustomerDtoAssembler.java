package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.customer.dtos.CustomerReservationSeatDto;

public class CustomerDtoAssembler {
    public CustomerReservationSeatDto customerReservationSeatToDto(String customerName, int customerSeatNumber,
                                                                   String customerReservationName) {
        return new CustomerReservationSeatDto(customerName, customerSeatNumber, customerReservationName);
    }
}
