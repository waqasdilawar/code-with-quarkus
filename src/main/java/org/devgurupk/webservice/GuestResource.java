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

import org.devgurupk.data.entity.GuestEntity;
import org.devgurupk.data.repository.GuestRepository;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;

@Path("/guests")
@Produces("application/json")
public class GuestResource {

  final GuestRepository guestRepository;

  public GuestResource(GuestRepository guestRepository) {
    this.guestRepository = guestRepository;
  }

  @GET
  public List<GuestEntity> getGuests() {
    return this.guestRepository.listAll();
  }

  @POST
  @ResponseStatus(201)
  @Transactional
  public GuestEntity addGuest(GuestEntity guest) {
    this.guestRepository.persist(guest);
    return guest;
  }

  @GET
  @Path("/{id}")
  public GuestEntity getGuest(@RestPath(value = "id") long id) {
    GuestEntity guest = this.guestRepository.findById(id);
    if (guest == null) {
      throw new WebApplicationException(Response.status(404).entity(id + " doesn't exist").build());
    }
    return guest;
  }

  @PUT
  @Path("/{id}")
  @ResponseStatus(204)
  @Transactional
  public void updateGuest(@RestPath(value = "id") long id, GuestEntity guest) {
    if (guest.getGuestId() != id) {
      throw new WebApplicationException(
        Response.status(400).entity(guest.getGuestId() + " doesn't match path").build());
    }
    GuestEntity guestEntity = this.guestRepository.findById(id);
    guestEntity.setAddress(guest.getAddress());
    guestEntity.setCountry(guest.getCountry());
    guestEntity.setEmailAddress(guest.getEmailAddress());
    guestEntity.setFirstName(guest.getFirstName());
    guestEntity.setLastName(guest.getLastName());
    guestEntity.setPhoneNumber(guest.getPhoneNumber());
    this.guestRepository.persist(guestEntity);
  }

  @DELETE
  @Path("/{id}")
  @ResponseStatus(205)
  @Transactional
  public void deleteGuest(@RestPath(value = "id") long id) {
    this.guestRepository.deleteById(id);
  }
}
