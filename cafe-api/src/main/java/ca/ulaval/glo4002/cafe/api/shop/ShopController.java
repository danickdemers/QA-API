package ca.ulaval.glo4002.cafe.api.shop;

import java.util.Arrays;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.MissingParameterException;
import ca.ulaval.glo4002.cafe.api.shop.dtos.ConfigRequest;
import ca.ulaval.glo4002.cafe.api.shop.exceptions.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.application.shop.ShopService;
import ca.ulaval.glo4002.cafe.application.shop.dtos.ShopDto;
import ca.ulaval.glo4002.cafe.domain.shop.GroupTipRate;
import ca.ulaval.glo4002.cafe.domain.shop.strategy.ReservationType;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Canada.Province;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.Country;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.UnitedStates.State;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @Path("layout")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response layout() {
        ShopDto shopDto = shopService.getShop();
        return Response.status(Response.Status.OK).entity(shopDto).build();
    }

    @Path("close")
    @POST
    public Response close() {
        shopService.resetShop();
        shopService.resetCustomers();
        shopService.resetInventory();
        return Response.ok().build();
    }

    @Path("config")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postConfig(ConfigRequest request) {
        if (!request.allParametersAreDefined()) {
            throw new MissingParameterException();
        }

        var optionalReservationType = getOptionalReservationType(request.groupReservationMethod);
        Country country = Country.fromString(request.country);
        Province province = Province.fromString(request.province);
        State state = State.fromString(request.state);
        GroupTipRate groupTipRate = new GroupTipRate(request.groupTipRate);

        shopService.config(request.organizationName, request.cubeSize, optionalReservationType,
                           country, province, state, groupTipRate);

        return Response.ok().build();
    }

    private ReservationType getOptionalReservationType(String request) {
        var optionalReservationType =
                Arrays.stream(ReservationType.values())
                        .filter(type -> type.getName().equals(request)).findFirst();

        if (optionalReservationType.isEmpty()) {
            throw new InvalidGroupReservationMethodException();
        }

        return optionalReservationType.get();
    }
}
