package ca.ulaval.glo4002.cafe.api.customer;

import java.net.URI;

import ca.ulaval.glo4002.cafe.api.customer.dtos.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.customer.dtos.CheckOutRequest;
import ca.ulaval.glo4002.cafe.api.customer.dtos.OrderRequest;
import ca.ulaval.glo4002.cafe.api.shared.exceptions.MissingParameterException;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.customer.dtos.CustomerBillDto;
import ca.ulaval.glo4002.cafe.application.customer.dtos.CustomerReservationSeatDto;
import ca.ulaval.glo4002.cafe.application.customer.dtos.OrdersDto;
import ca.ulaval.glo4002.cafe.application.shop.ShopService;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class CustomerController {
    private final CustomerService customerService;
    private final ShopService shopService;

    public CustomerController(CustomerService customerService, ShopService shopService) {
        this.customerService = customerService;
        this.shopService = shopService;
    }

    @Path("customers/{CUSTOMER_ID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("CUSTOMER_ID") String customerId) {
        CustomerReservationSeatDto customerSeat = customerService.getCustomer(new CustomerId(customerId));
        return Response.status(Response.Status.OK).entity(customerSeat).build();
    }

    @Path("check-in")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkIn(CheckInRequest request) {
        if (!request.allParametersAreDefined()) {
            throw new MissingParameterException();
        }
        customerService.checkInCustomer(new CustomerId(request.customerId), request.customerName, request.groupName);
        return Response
                .created(URI.create("/customers/" + request.customerId))
                .build();
    }

    @Path("customers/{CUSTOMER_ID}/orders")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putOrders(@PathParam("CUSTOMER_ID") String customerId, OrderRequest orderRequest) {
        customerService.putOrders(new CustomerId(customerId), orderRequest.orders);
        return Response.status(Response.Status.OK).build();
    }

    @Path("customers/{CUSTOMER_ID}/orders")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@PathParam("CUSTOMER_ID") String customerId) {
        OrdersDto ordersDto = customerService.getOrders(new CustomerId(customerId));
        return Response.status(Response.Status.OK).entity(ordersDto).build();
    }

    @Path("checkout")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkOut(CheckOutRequest request) {
        if (!request.allParametersAreDefined()) {
            throw new MissingParameterException();
        }
        customerService.checkOutCustomer(new CustomerId(request.customerId));
        shopService.freeSeat(new CustomerId(request.customerId));
        return Response
                .created(URI.create("/customers/" + request.customerId + "/bill"))
                .build();
    }

    @Path("customers/{CUSTOMER_ID}/bill")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBill(@PathParam("CUSTOMER_ID") String customerId) {
        CustomerBillDto customerBill = customerService.getBill(new CustomerId(customerId));
        return Response.status(Response.Status.OK).entity(customerBill).build();
    }
}
