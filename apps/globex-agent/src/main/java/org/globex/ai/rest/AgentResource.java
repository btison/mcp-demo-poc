package org.globex.ai.rest;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/v1")
@Authenticated
public class AgentResource {

    @Inject
    JsonWebToken jwt;

    @Path("/request")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handleUserRequest(String request) {
        String userEmail = jwt.claim(Claims.email).orElse("").toString();
        return Response.ok().build();
    }

}
