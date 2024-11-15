package org.devgurupk.webservice;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;

import java.util.List;

import org.devgurupk.data.entity.GuestEntity;
import org.devgurupk.data.repository.GuestRepository;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;
import io.quarkus.logging.Log;

@Path("/guests")
@Produces("application/json")
public class GuestResource {

  private final GuestRepository guestRepository;

  @Inject
  public GuestResource(GuestRepository guestRepository) {
    this.guestRepository = guestRepository;
  }

  @GET
  public List<GuestEntity> getGuests() {
    Log.debugf("Fetching all guests");
    List<GuestEntity> guests = this.guestRepository.listAll();
    Log.debugf("Retrieved %d guests", guests.size());
    return guests;
  }

  @POST
  @ResponseStatus(201)
  @Transactional
  public GuestEntity addGuest(GuestEntity guest) {
    Log.debugf("Adding new guest: %s", guest);
    this.guestRepository.persist(guest);
    Log.infof("Added new guest with ID: %d", guest.getGuestId());
    return guest;
  }

  @GET
  @Path("/{id}")
  public GuestEntity getGuest(@RestPath(value = "id") long id) {
    Log.debugf("Fetching guest with ID: %d", id);
    GuestEntity guest = this.guestRepository.findById(id);
    if (guest == null) {
      Log.warnf("Guest with ID %d not found", id);
      throw new WebApplicationException(Response.status(404).entity(id + " doesn't exist").build());
    }
    return guest;
  }

  @PUT
  @Path("/{id}")
  @ResponseStatus(204)
  @Transactional
  public void updateGuest(@RestPath(value = "id") long id, GuestEntity guest) {
    Log.debugf("Updating guest with ID: %d", id);
    if (guest.getGuestId() != id) {
      Log.warnf("Mismatch between path ID (%d) and guest ID (%d)", id, guest.getGuestId());
      throw new WebApplicationException(
        Response.status(400).entity(guest.getGuestId() + " doesn't match path").build());
    }
    GuestEntity guestEntity = this.guestRepository.findById(id);
    if (guestEntity == null) {
      Log.warnf("Guest with ID %d not found for update", id);
      throw new WebApplicationException(Response.status(404).entity(id + " doesn't exist").build());
    }
    updateGuestFields(guestEntity, guest);
    this.guestRepository.persist(guestEntity);
    Log.infof("Updated guest with ID: %d", id);
  }

  @DELETE
  @Path("/{id}")
  @ResponseStatus(205)
  @Transactional
  public void deleteGuest(@RestPath(value = "id") long id) {
    Log.debugf("Deleting guest with ID: %d", id);
    boolean deleted = this.guestRepository.deleteById(id);
    if (!deleted) {
      Log.warnf("Guest with ID %d not found for deletion", id);
      throw new WebApplicationException(Response.status(404).entity(id + " doesn't exist").build());
    }
    Log.infof("Deleted guest with ID: %d", id);
  }

  private void updateGuestFields(GuestEntity target, GuestEntity source) {
    target.setAddress(source.getAddress());
    target.setCountry(source.getCountry());
    target.setEmailAddress(source.getEmailAddress());
    target.setFirstName(source.getFirstName());
    target.setLastName(source.getLastName());
    target.setPhoneNumber(source.getPhoneNumber());
  }
}