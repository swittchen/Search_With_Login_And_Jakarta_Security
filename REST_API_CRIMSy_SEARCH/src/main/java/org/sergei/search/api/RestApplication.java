package org.sergei.search.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.sergei.cors.CorsFilter;
import org.sergei.security.AuthResource;
import org.sergei.security.SecurityFilter;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {

//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> resources = new HashSet<>();
//        resources.add(PingResource.class);
//        resources.add(SearchResource.class);
//        resources.add(CorsFilter.class);
//        resources.add(AuthResource.class);
//        resources.add(SecurityFilter.class);
//        return resources;
//    }


}
