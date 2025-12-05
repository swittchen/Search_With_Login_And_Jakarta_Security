package org.sergei.search.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import org.sergei.requestDTO.SearchBodyDTO;

@RequestScoped
@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN", "USER"})
public class SearchResource {


    // TEST
//    @POST
//    public Response getSearchResults(SearchBodyDTO body) {
//        body.setStatus("Success!");
//        return Response.ok(body).build();
//    }

    @GET
    public Response getAccess(){
        return Response.ok().build();
    }

    @POST
    public Response getSearchResults(SearchBodyDTO body) {
   // public Response getSearchResults(SearchBodyDTO body, @HeaderParam("Authorization") String authorizationHeader) {

        // Get Bearer token
       // String bearerToken = extractBearerToken(authorizationHeader);

        //SearchRequest request
        body.setStatus("FERIALIS");
        return Response.ok(body).build();

    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new WebApplicationException(401);
        }
        return authorizationHeader.substring("Bearer ".length());
    }

}
