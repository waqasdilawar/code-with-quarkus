package org.devgurupk.webservice;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.devgurupk.model.GreetingResponse;
import org.devgurupk.service.GreetingService;

@Path("/hello")
public class GreetingResource {

  private final GreetingService greetingService;

  public GreetingResource(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  @GET
  public GreetingResponse hello() {
    return this.greetingService.getGreeting();
  }
}