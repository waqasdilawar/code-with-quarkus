package org.devgurupk.model;

import java.util.List;

public record GreetingResponse(
  String greeting,
  int dieRoll,
  List<String> roomNumbers,
  List<String> emailAddresses
) {
}
