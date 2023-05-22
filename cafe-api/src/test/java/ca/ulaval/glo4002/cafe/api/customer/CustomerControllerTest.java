package ca.ulaval.glo4002.cafe.api.customer;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.api.customer.dtos.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.customer.dtos.CheckOutRequest;
import ca.ulaval.glo4002.cafe.api.customer.dtos.OrderRequest;
import ca.ulaval.glo4002.cafe.api.shared.exceptions.MissingParameterException;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.customer.dtos.CustomerBillDto;
import ca.ulaval.glo4002.cafe.application.shop.ShopService;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    private static final String CUSTOMER_ID = "aaaaa-4fce-44a0-aa41-c41c5777e679";
    private static final String CUSTOMER_NAME = "Keanu Reeves";
    @Mock
    private CustomerService customerService;
    @Mock
    private ShopService shopService;

    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerService, shopService);
    }

    @Test
    void whenGetCustomer_thenGetCustomerFromService() {
        customerController.getCustomer(CUSTOMER_ID);

        verify(customerService).getCustomer(new CustomerId(CUSTOMER_ID));
    }

    @Test
    void whenGetCustomer_thenReturnsValidStatusResponse() {
        Response response = customerController.getCustomer(CUSTOMER_ID);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void givenNullCustomerId_whenCheckIn_thenThrowsMissingParameterException() {
        var checkInRequest = new CheckInRequest(null, CUSTOMER_NAME, null);

        assertThrows(MissingParameterException.class, () -> customerController.checkIn(checkInRequest));
    }

    @Test
    void givenNullCustomerName_whenCheckIn_thenThrowsMissingParameterException() {
        var checkInRequest = new CheckInRequest(CUSTOMER_ID, null, null);

        assertThrows(MissingParameterException.class, () -> customerController.checkIn(checkInRequest));
    }

    @Test
    void givenNullCustomerIdAndNullCustomerName_whenCheckIn_thenThrowsMissingParameterException() {
        var checkInRequest = new CheckInRequest(null, null, null);

        assertThrows(MissingParameterException.class, () -> customerController.checkIn(checkInRequest));
    }

    @Test
    void givenNullGroupName_whenCheckIn_thenDoesNotThrowException() {
        var checkInRequest = new CheckInRequest(CUSTOMER_ID, CUSTOMER_NAME, null);

        assertDoesNotThrow(() -> customerController.checkIn(checkInRequest));
    }

    @Test
    void givenCustomerSeatedAndSeatsAreAvailable_whenCheckingIn_thenCreateCustomer() {
        var checkInRequest = getValidCheckInRequestWithoutGroupName();

        customerController.checkIn(checkInRequest);

        verify(customerService).checkInCustomer(new CustomerId(CUSTOMER_ID), CUSTOMER_NAME, null);
    }

    @Test
    void givenCustomerNotSeatedAndSeatsAreAvailable_whenCheckingIn_thenReturnResponse() {
        var checkInRequest = getValidCheckInRequestWithoutGroupName();

        var response = customerController.checkIn(checkInRequest);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
        assertEquals("/customers/" + CUSTOMER_ID,
                     response.getLocation().toString());
    }

    @Test
    void whenPutOrders_thenDelegateToCustomerService() {
        var orderRequest = new OrderRequest(List.of());

        customerController.putOrders(CUSTOMER_ID, orderRequest);

        verify(customerService).putOrders(new CustomerId(CUSTOMER_ID), List.of());
    }

    @Test
    void whenPutOrders_thenReturnResponseOk() {
        var orderRequest = new OrderRequest(List.of());

        var response = customerController.putOrders(CUSTOMER_ID, orderRequest);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void whenGetOrders_thenDelegateToCustomerService() {
        customerController.getOrders(CUSTOMER_ID);

        verify(customerService).getOrders(new CustomerId(CUSTOMER_ID));
    }

    @Test
    void whenGetOrders_thenReturnResponseOk() {
        Response response = customerController.getOrders(CUSTOMER_ID);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void givenNullCustomerId_whenCheckOut_thenThrowsMissingParameterException() {
        var checkOutRequest = new CheckOutRequest(null);

        assertThrows(MissingParameterException.class, () -> customerController.checkOut(checkOutRequest));
    }

    @Test
    void giveValidRequest_whenCheckOut_thenCallServices() {
        var checkOutRequest = new CheckOutRequest(CUSTOMER_ID);

        customerController.checkOut(checkOutRequest);

        verify(customerService).checkOutCustomer(new CustomerId(CUSTOMER_ID));
        verify(shopService).freeSeat(new CustomerId(CUSTOMER_ID));
    }

    @Test
    void whenCheckOut_thenReturnsValidStatusResponse() {
        var checkOutRequest = new CheckOutRequest(CUSTOMER_ID);

        var response = customerController.checkOut(checkOutRequest);

        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test
    void givenValidCustomerId_whenGetBill_thenResponseOk() {
        when(customerService.getBill(new CustomerId(CUSTOMER_ID))).thenReturn(null);

        var result = customerController.getBill(CUSTOMER_ID);

        assertEquals(Response.Status.OK, result.getStatusInfo());
    }

    @Test
    void givenValidCustomerId_whenGetBill_thenBillReturnedAsEntity() {
        CustomerBillDto customerBillDto = new CustomerBillDto(null, 0, 0, 0, 0);
        when(customerService.getBill(new CustomerId(CUSTOMER_ID))).thenReturn(customerBillDto);

        var result = customerController.getBill(CUSTOMER_ID);

        assertEquals(customerBillDto, result.getEntity());
    }

    private CheckInRequest getValidCheckInRequestWithoutGroupName() {
        return new CheckInRequest(CUSTOMER_ID, CUSTOMER_NAME, null);
    }
}
