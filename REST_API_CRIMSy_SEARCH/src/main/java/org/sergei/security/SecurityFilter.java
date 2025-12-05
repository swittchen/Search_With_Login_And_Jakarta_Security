package org.sergei.security;

import jakarta.annotation.Priority;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sergei.security.support_classes.AuthToken;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
@ApplicationScoped
public class SecurityFilter implements ContainerRequestFilter {

    Logger logger = LogManager.getLogger(this.getClass());

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    private TokenStore tokenStore;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();

        // 1) Preflight-Requests (OPTIONS) NICHT authentifizieren
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return;
        }

        // 2) Login-Endpunkt ist offen
        if (path.equals("auth/login")) {  // bei @ApplicationPath("/api") ist path hier "auth/login"
            return;
        }
        logger.info("SecurityFilter path = {}, method = {}", path, method);


        // 3) Rollen nur prüfen, wenn @RolesAllowed vorhanden
        RolesAllowed rolesAllowed = resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class);
        if (rolesAllowed == null) {
            rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
        }
        if (rolesAllowed == null) {
            // Kein @RolesAllowed → Endpoint ist öffentlich zugänglich
            return;
        }

        // Ab hier: geschützte Endpoints → Token prüfen
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abort(requestContext);
            return;
        }

        String tokenValue = authHeader.substring("Bearer ".length()).trim();
        AuthToken token = tokenStore.findByToken(tokenValue);
        if (token == null) {
            abort(requestContext);
            return;
        }

        Set<String> requiredRoles = new HashSet<>(Arrays.asList(rolesAllowed.value()));

        if (!requiredRoles.contains(token.getRole().name())) {
            abort(requestContext);
            return;
        }

        // SecurityContext setzen
        final SecurityContext current = requestContext.getSecurityContext();
        SecurityContext sc = new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> token.getUsername();
            }

            @Override
            public boolean isUserInRole(String role) {
                return token.getRole().name().equals(role);
            }

            @Override
            public boolean isSecure() {
                return current != null && current.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        };
        requestContext.setSecurityContext(sc);
    }


    private void abort(ContainerRequestContext ctx) {
        ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
