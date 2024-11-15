package org.devgurupk.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import org.devgurupk.data.entity.GuestEntity;
import org.devgurupk.data.entity.RoomEntity;
import org.devgurupk.data.repository.GuestRepository;
import org.devgurupk.data.repository.RoomRepository;
import org.devgurupk.model.GreetingResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GreetingService {

  @ConfigProperty(name = "greeting.text")
  private String greeting;

  final DieRollerService dieRollerService;
  final RoomRepository roomRepository;
  final GuestRepository guestRepository;

  public GreetingService(
    DieRollerService dieRollerService, RoomRepository roomRepository, GuestRepository guestRepository) {
    this.dieRollerService = dieRollerService;
    this.roomRepository = roomRepository;
    this.guestRepository = guestRepository;
  }

  public GreetingResponse getGreeting() {
    int dieRoll = this.dieRollerService.getRoll();

    List<RoomEntity> rooms = this.roomRepository.listAll();
    List<String> roomNumbers = rooms.stream()
      .map(RoomEntity::getRoomNumber)
      .collect(Collectors.toList());

    List<GuestEntity> guests = this.guestRepository.listAll();
    List<String> emailAddresses = guests.stream()
      .map(GuestEntity::getEmailAddress)
      .collect(Collectors.toList());

    return new GreetingResponse(
      this.greeting,
      dieRoll,
      roomNumbers,
      emailAddresses
    );
  }
}
