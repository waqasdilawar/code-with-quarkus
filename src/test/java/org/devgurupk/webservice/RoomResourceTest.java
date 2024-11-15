package org.devgurupk.webservice;


import io.quarkus.test.junit.QuarkusTest;
import org.devgurupk.data.entity.RoomEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomResourceTest {

    @Test
    @Order(0)
    public void testGetAllRooms() {
        given()
          .when().get("/rooms")
          .then()
             .statusCode(200)
             .body("$", hasSize(greaterThan(0)));
    }

    @Test
    @Order(2)
    public void testGetRoomById() {
        given()
          .when().get("/rooms/2")
          .then()
             .statusCode(200)
             .body("roomId", is(2));
    }

    @Test
    @Order(1)
    public void testCreateRoom() {
        RoomEntity room = new RoomEntity();
        room.setName("New Room");
        room.setRoomNumber("101");
        room.setBedInfo("1 King");

        given()
          .contentType("application/json")
          .body(room)
          .when().post("/rooms")
          .then()
             .statusCode(201)
             .body("name", is("New Room"))
             .body("roomNumber", is("101"))
             .body("bedInfo", is("1 King"));
    }

    @Test
    @Order(4)
    public void testUpdateRoom() {
        RoomEntity room = new RoomEntity();
        room.setName("Updated Room");
        room.setRoomId(2);
        room.setRoomNumber("102");
        room.setBedInfo("2 Queen");

        given()
          .contentType("application/json")
          .body(room)
          .when().put("/rooms/2")
          .then()
             .statusCode(204)
             .body("name", is("Updated Room"))
             .body("roomNumber", is("102"))
             .body("bedInfo", is("2 Queen"));
    }

    @Test
    @Order(5)
    public void testDeleteRoom() {
        given()
          .when().delete("/rooms/2")
          .then()
             .statusCode(205);
    }
}