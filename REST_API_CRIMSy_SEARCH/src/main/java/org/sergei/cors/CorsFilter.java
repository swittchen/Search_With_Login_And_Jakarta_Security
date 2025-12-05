package org.sergei.cors;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Provider
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Обработка preflight-запроса OPTIONS
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            Response.ResponseBuilder builder = Response.ok();
            builder.header("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
            builder.header("Access-Control-Allow-Credentials", "true");
            builder.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            builder.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            requestContext.abortWith(builder.build());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        // Для обычных (не-OPTIONS) ответов
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
    }
}
