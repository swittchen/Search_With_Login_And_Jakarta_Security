package org.sergei.security;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sergei.requestDTO.LoginRequestDTO;
import org.sergei.responseDTO.LoginResponseDTO;
import org.sergei.security.support_classes.AuthToken;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuthResource {

    @PostConstruct
    public void init() {
        System.out.println("AuthResource CDI init, tokenStore = " + tokenStore);
    }


    Logger logger = LogManager.getLogger(this.getClass());

    @Inject
    private TokenStore tokenStore;

    @POST
    @Path("/login")
    public Response login(LoginRequestDTO requestDTO) {
        logger.info("LOGIN request DTO = {}", requestDTO.toString());

        if (requestDTO == null) {
            logger.warn("Login: requestDTO is null");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Empty request body\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        logger.info("LOGIN user = {}, passwordNull = {}",
                requestDTO.getUsername(),
                requestDTO.getPassword() == null);

        if (tokenStore == null) {
            logger.error("Login: tokenStore is NULL!");
            return Response.serverError()
                    .entity("{\"error\":\"TokenStore not initialized\"}")
                    .build();
        }

        AuthToken token = tokenStore.authenticate(requestDTO.getUsername(), requestDTO.getPassword());
        if (token == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Invalid username or password\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        LoginResponseDTO body = new LoginResponseDTO(
                token.getToken(),
                requestDTO.getUsername(),
                token.getRole()
        );

        return Response.ok(body).build();
    }



//    @POST
//    @Path("/login")
//    public Response login(LoginRequestDTO requestDTO) {
//        logger.info("I AM IN");
//        return Response.ok("{\"message\":\"test ok\"}").build();
//    }


//    @POST
//    @Path("/login")
//    public Response login(LoginRequestDTO requestDTO) {
//
//        AuthToken token = tokenStore.authenticate(requestDTO.getUsername(), requestDTO.getPassword());
//        if (token == null) {
//            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
//        }
//        System.out.println("LOGIN for user: " + requestDTO.getUsername());
//        System.out.println("TOKEN: " + token);
//
//
//        LoginResponseDTO body = new LoginResponseDTO(
//                token.getToken(),
//                requestDTO.getUsername(),
//                token.getRole()
//        );
//
//        return Response.ok(body).build();
//    }
}
