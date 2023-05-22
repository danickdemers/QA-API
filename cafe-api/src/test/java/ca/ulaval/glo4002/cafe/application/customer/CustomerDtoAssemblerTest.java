package ca.ulaval.glo4002.cafe.application.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.customer.dtos.CustomerReservationSeatDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerDtoAssemblerTest {
    public static final int SEAT_NUMBER = 1;
    public static final String CUSTOMER_NAME = "Keanu Reeves";

    private CustomerDtoAssembler customerAssembler;

    @BeforeEach
    void setUp() {
        customerAssembler = new CustomerDtoAssembler();
    }

    @Test
    void givenCustomerNameSeatNumberReservationName_whenToCustomerReservationSeatDto_thenCreateDto() {
        CustomerReservationSeatDto customerGroupSeatDto =
                customerAssembler.customerReservationSeatToDto(CUSTOMER_NAME, SEAT_NUMBER, null);

        assertEquals(CUSTOMER_NAME, customerGroupSeatDto.name());
        assertEquals(SEAT_NUMBER, customerGroupSeatDto.seatNumber());
        assertEquals(null, customerGroupSeatDto.groupName());
    }
}
