package ca.ulaval.glo4002.cafe.api.reservation;

import ca.ulaval.glo4002.cafe.api.reservation.dtos.ReservationRequest;
import ca.ulaval.glo4002.cafe.api.shared.exceptions.MissingParameterException;
import ca.ulaval.glo4002.cafe.application.reservations.ReservationService;
import ca.ulaval.glo4002.cafe.application.reservations.dtos.ReservationsDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        ReservationsDto reservationsDto = reservationService.getReservations();

        return Response.ok(reservationsDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reserve(ReservationRequest reservationRequest) {
        if (!reservationRequest.allParametersAreDefined()) {
            throw new MissingParameterException();
        }
        reservationService.reserve(reservationRequest.groupName, reservationRequest.groupSize);
        return Response.ok().build();
    }
}
