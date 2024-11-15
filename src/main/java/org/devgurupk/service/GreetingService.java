package org.devgurupk.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import org.devgurupk.data.entity.GuestEntity;
import org.devgurupk.data.entity.RoomEntity;
import org.devgurupk.data.repository.GuestRepository;
import org.devgurupk.data.repository.RoomRepository;
import org.devgurupk.model.GreetingResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.Log;

@ApplicationScoped
public class GreetingService {

  @ConfigProperty(name = "greeting.text")
  String greeting;

  final DieRollerService dieRollerService;
  final RoomRepository roomRepository;
  final GuestRepository guestRepository;

  @Inject
  public GreetingService(
    DieRollerService dieRollerService,
    RoomRepository roomRepository,
    GuestRepository guestRepository) {
    this.dieRollerService = dieRollerService;
    this.roomRepository = roomRepository;
    this.guestRepository = guestRepository;
  }

  public GreetingResponse getGreeting() {
    Log.debug("Getting greeting...");
    int dieRoll = this.dieRollerService.getRoll();
    Log.debugf("Die roll: %d", dieRoll);

    List<RoomEntity> rooms = this.roomRepository.listAll();
    Log.debugf("Rooms: %d", rooms.size());
    List<String> roomNumbers = rooms.stream()
      .map(RoomEntity::getRoomNumber)
      .collect(Collectors.toList());

    List<GuestEntity> guests = this.guestRepository.listAll();
    Log.debugf("Guests: %d", guests.size());
    List<String> emailAddresses = guests.stream()
      .map(GuestEntity::getEmailAddress)
      .collect(Collectors.toList());

    Log.debug("Creating greeting response...");
    return new GreetingResponse(
      this.greeting,
      dieRoll,
      roomNumbers,
      emailAddresses
    );
  }
}