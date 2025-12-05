package org.sergei.search.api;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequestScoped
@Path("/ping")
public class PingResource {
    LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Berlin"));
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    String date = now.format(formatterDate);
    String time = now.format(formatterTime);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String ping() {
        return String.format("{\"name\":\"Sergei\",\"surname\":\"Wittchen\",\"date\": \"%s\",\"time\": \"%s\"}", date, time);
    }
}
