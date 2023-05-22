package ca.ulaval.glo4002.cafe.application.customer;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.cafe.application.customer.dtos.CustomerBillDto;
import ca.ulaval.glo4002.cafe.application.customer.dtos.CustomerReservationSeatDto;
import ca.ulaval.glo4002.cafe.application.customer.dtos.OrdersDto;
import ca.ulaval.glo4002.cafe.application.inventory.IngredientInventoryService;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.Billable;
import ca.ulaval.glo4002.cafe.domain.customer.Coffee;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Menu;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.DuplicateCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.shop.CustomerInformation;
import ca.ulaval.glo4002.cafe.domain.shop.Shop;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;

public class CustomerService {
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;
    private final CustomerDtoAssembler customerAssembler;
    private final CustomerBillAssembler customerBillAssembler;
    private final IngredientInventoryService inventoryService;
    private final CustomerFactory customerFactory;
    private final OrdersDtoAssembler ordersDtoAssembler;

    public CustomerService(CustomerRepository customerRepository,
                           ShopRepository shopRepository, CustomerDtoAssembler customerAssembler,
                           CustomerBillAssembler customerBillAssembler,
                           IngredientInventoryService ingredientInventoryService,
                           CustomerFactory customerFactory,
                           OrdersDtoAssembler ordersDtoAssembler) {
        this.customerRepository = customerRepository;
        this.shopRepository = shopRepository;
        this.customerAssembler = customerAssembler;
        this.customerBillAssembler = customerBillAssembler;
        this.ordersDtoAssembler = ordersDtoAssembler;
        this.inventoryService = ingredientInventoryService;
        this.customerFactory = customerFactory;
    }

    public void checkInCustomer(CustomerId id, String name, String reservationName) {
        Shop shop = shopRepository.getShop();
        var customer = customerFactory.create(id, name, reservationName);
        if (customerRepository.exists(customer.getId())) {
            throw new DuplicateCustomerIdException();
        }

        if (reservationName != null) {
            shop.assignCustomerToAReservationSeat(id, reservationName);
        } else {
            shop.assignCustomerToAnAvailableSeat(id);
        }

        customerRepository.save(customer);
        shopRepository.save(shop);
    }

    public CustomerReservationSeatDto getCustomer(CustomerId id) {
        Shop shop = shopRepository.getShop();
        CustomerInformation customerInfo = shop.retrieveCustomerSeatInformation(id);
        Customer customer = customerRepository.findById(id);

        return customerAssembler.customerReservationSeatToDto(customer.getName(), customerInfo.getSeatNumber(),
                                                              customerInfo.getReservationName());
    }

    public void putOrders(CustomerId customerId, List<String> orders) {
        Customer customer = customerRepository.findById(customerId);
        Menu menu = shopRepository.getShop().getMenu();

        List<Coffee> coffeeOrders = new ArrayList<>();
        for (String order: orders) {
            coffeeOrders.add(menu.getCoffee(order));
        }

        inventoryService.removeInventoryForOrder(coffeeOrders);
        customer.putOrders(coffeeOrders);

        customerRepository.update(customer);
    }

    public OrdersDto getOrders(CustomerId customerId) {
        Customer customer = customerRepository.findById(customerId);
        return ordersDtoAssembler.ordersToOrdersDto(customer.getOrdersString());
    }

    public void checkOutCustomer(CustomerId customerId) {
        Customer customer = customerRepository.findById(customerId);
        customer.checkOut();
        customerRepository.update(customer);
    }

    public CustomerBillDto getBill(CustomerId customerId) {
        Billable customer = customerRepository.findById(customerId);
        Shop shop = shopRepository.getShop();
        Bill bill = customer.createBill(shop.getTaxer(), shop.getGroupTipRate());

        return customerBillAssembler.billToCustomerBillDto(bill);
    }
}
