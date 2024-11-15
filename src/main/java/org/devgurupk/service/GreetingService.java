package org.devgurupk.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

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
    GreetingResponse response = new GreetingResponse();
    response.setGreeting(this.greeting);
    response.setDieRoll(this.dieRollerService.getRoll());
    List<RoomEntity> rooms = this.roomRepository.listAll();
    List<String> roomNumbers = new ArrayList<>();
    rooms.forEach(room -> roomNumbers.add(room.getRoomNumber()));
    response.setRoomNumbers(roomNumbers);
    List<GuestEntity> guests = this.guestRepository.listAll();
    List<String> emailAddresses = new ArrayList<>();
    guests.forEach(guest -> emailAddresses.add(guest.getEmailAddress()));
    response.setEmailAddresses(emailAddresses);
    return response;
  }
}
