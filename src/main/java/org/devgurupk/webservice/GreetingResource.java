package org.devgurupk.webservice;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.devgurupk.model.GreetingResponse;
import org.devgurupk.service.GreetingService;

@Path("/hello")
public class GreetingResource {

  private final GreetingService greetingService;

  @Inject
  public GreetingResource(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  @GET
  public GreetingResponse hello() {
    Log.debugf("Received request for greeting");
    GreetingResponse response = this.greetingService.getGreeting();
    Log.debugf("Returning greeting response: %s", response);
    return response;
  }
}