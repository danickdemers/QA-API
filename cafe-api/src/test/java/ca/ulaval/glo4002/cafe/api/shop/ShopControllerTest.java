package ca.ulaval.glo4002.cafe.api.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.api.shop.dtos.ConfigRequest;
import ca.ulaval.glo4002.cafe.api.shop.exceptions.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.application.shop.ShopService;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.strategy.ReservationType;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada.Province;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Country;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates.State;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShopControllerTest {
    private static final String A_SHOP_NAME = "Hiss Kaffee";
    private static final int A_NB_OF_SEATS = 4;
    private static final Country A_COUNTRY = Country.CA;
    private static final Province A_PROVINCE = Province.QC;
    private static final State A_STATE = State.TX;
    private static final GroupTipRate A_GROUP_TIP_RATE = new GroupTipRate(0);
    @Mock
    private ShopService shopService;
    private ConfigRequest configRequest;
    private ShopController shopController;

    @BeforeEach
    void setUp() {
        configRequest = new ConfigRequest();
        shopController = new ShopController(shopService);
    }

    @Test
    void whenLayout_thenGetShopFromService() {
        try (var ignored = shopController.layout()) {
            verify(shopService).getShop();
        }
    }

    @Test
    void whenLayout_thenReturnsValidStatusResponse() {
        try (var response = shopController.layout()) {
            assertEquals(Response.Status.OK, response.getStatusInfo());
        }
    }

    @Test
    void whenClose_thenCloseFromServiceIsCalled() {
        try (var ignored = shopController.close()) {
            verify(shopService).resetShop();
            verify(shopService).resetCustomers();
            verify(shopService).resetShop();
        }
    }

    @Test
    void whenClose_thenReturnsValidStatusResponse() {
        Response result = shopController.close();

        assertEquals(Response.Status.OK, result.getStatusInfo());
    }

    @Test
    void givenWrongReservationMethod_whenPostConfig_thenInvalidGroupReservationMethodExceptionIsCalled() {
        configRequest = invalidConfigRequest();

        assertThrows(InvalidGroupReservationMethodException.class, () -> shopController.postConfig(configRequest));
    }

    @Test
    void whenPostConfig_thenConfigIsCalled() {
        ConfigRequest configRequest = validConfigRequest();

        try (var ignored = shopController.postConfig(configRequest)) {
            verify(shopService).config(A_SHOP_NAME, A_NB_OF_SEATS, ReservationType.DEFAULT, A_COUNTRY,
                                       A_PROVINCE, A_STATE, A_GROUP_TIP_RATE);
        }
    }

    @Test
    void givenConfigRequest_whenPostConfig_thenReturnsValidStatusResponse() {
        ConfigRequest configRequest = validConfigRequest();

        try (var response = shopController.postConfig(configRequest)) {
            assertEquals(Response.Status.OK, response.getStatusInfo());
        }
    }

    private ConfigRequest validConfigRequest() {
        configRequest.groupReservationMethod = "Default";
        configRequest.organizationName = A_SHOP_NAME;
        configRequest.cubeSize = A_NB_OF_SEATS;
        configRequest.country = "CA";
        configRequest.province = "QC";
        configRequest.state = "TX";
        configRequest.groupTipRate = 0.0;
        return configRequest;
    }

    private ConfigRequest invalidConfigRequest() {
        configRequest.groupReservationMethod = "Not valid";
        configRequest.organizationName = A_SHOP_NAME;
        configRequest.cubeSize = A_NB_OF_SEATS;
        configRequest.country = "CA";
        configRequest.province = "QC";
        configRequest.state = "TX";
        configRequest.groupTipRate = 0.0;
        return configRequest;
    }
}
