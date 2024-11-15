package org.devgurupk.webservice;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

import org.devgurupk.data.entity.RoomEntity;
import org.devgurupk.data.repository.RoomRepository;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;

@Path("/rooms")
@Produces("application/json")
public class RoomResource {

  final RoomRepository roomRepository;

  public RoomResource(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @GET
  public List<RoomEntity> getRooms() {
    return this.roomRepository.listAll();
  }

  @POST
  @ResponseStatus(201)
  @Transactional
  public RoomEntity addRoom(RoomEntity room) {
    this.roomRepository.persist(room);
    return room;
  }

  @GET
  @Path("/{id}")
  public RoomEntity getRoom(@RestPath(value = "id") long id) {
    RoomEntity entity = this.roomRepository.findById(id);
    if (entity == null) {
      throw new WebApplicationException(Response.status(404).entity(id + "doesn't exist").build());
    }
    return entity;
  }

  @PUT
  @Path("/{id}")
  @ResponseStatus(204)
  @Transactional
  public void updateRoom(@RestPath(value = "id") long id, RoomEntity room) {
    if (room.getRoomId() != id) {
      throw new WebApplicationException(Response.status(400).entity("ids don't match").build());
    }
    RoomEntity roomEntity = this.roomRepository.findById(id);
    roomEntity.setRoomNumber(room.getRoomNumber());
    roomEntity.setBedInfo(room.getBedInfo());
    roomEntity.setName(room.getName());
    this.roomRepository.persist(roomEntity);
  }

  @DELETE
  @Path("/{id}")
  @ResponseStatus(205)
  @Transactional
  public void deleteRoom(@RestPath(value = "id") long id) {
    this.roomRepository.deleteById(id);
  }
}
