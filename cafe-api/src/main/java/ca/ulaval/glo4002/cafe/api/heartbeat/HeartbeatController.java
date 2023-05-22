package ca.ulaval.glo4002.cafe.api.heartbeat;

import ca.ulaval.glo4002.cafe.api.heartbeat.dtos.HeartbeatResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/heartbeat")
@Produces(MediaType.APPLICATION_JSON)
public class HeartbeatController {
    @GET
    public HeartbeatResponse heartbeat(@QueryParam("token") String token) {
        return new HeartbeatResponse(token);
    }

    @POST
    public Response postHeartbeat(HeartbeatResponse r) {
        return Response.ok().entity(r).build();
    }
}
