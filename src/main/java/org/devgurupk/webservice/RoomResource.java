package org.devgurupk.webservice;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;

import org.devgurupk.data.entity.RoomEntity;
import org.devgurupk.data.repository.RoomRepository;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;
import io.quarkus.logging.Log;

@Path("/rooms")
@Produces("application/json")
public class RoomResource {

    final RoomRepository roomRepository;

    @Inject
    public RoomResource(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GET
    public List<RoomEntity> getRooms() {
        Log.debugf("Fetching all rooms");
        return this.roomRepository.listAll();
    }

    @POST
    @ResponseStatus(201)
    @Transactional
    public RoomEntity addRoom(RoomEntity room) {
        Log.debugf("Adding new room");
        this.roomRepository.persist(room);
        return room;
    }

    @GET
    @Path("/{id}")
    public RoomEntity getRoom(@RestPath(value = "id") long id) {
        Log.debugf("Fetching room with ID: %d", id);
        RoomEntity entity = this.roomRepository.findById(id);
        if (entity == null) {
            Log.warnf("Room with ID %d not found", id);
            throw new WebApplicationException(Response.status(404).entity(id + " doesn't exist").build());
        }
        return entity;
    }

    @PUT
    @Path("/{id}")
    @ResponseStatus(204)
    @Transactional
    public void updateRoom(@RestPath(value = "id") long id, RoomEntity room) {
        Log.debugf("Updating room with ID: %d", id);
        if (room.getRoomId() != id) {
            Log.warnf("Mismatch between path ID (%d) and room ID (%d)", id, room.getRoomId());
            throw new WebApplicationException(Response.status(400).entity("ids don't match").build());
        }
        RoomEntity roomEntity = this.roomRepository.findById(id);
        if (roomEntity == null) {
            Log.warnf("Room with ID %d not found for update", id);
            throw new WebApplicationException(Response.status(404).entity(id + " doesn't exist").build());
        }
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
        Log.debugf("Deleting room with ID: %d", id);
        if (!this.roomRepository.deleteById(id)) {
            Log.warnf("Room with ID %d not found for deletion", id);
            throw new WebApplicationException(Response.status(404).entity(id + " doesn't exist").build());
        }
    }
}