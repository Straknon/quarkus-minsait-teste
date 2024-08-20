package org.gs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import com.google.gson.Gson;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieResourceTestIT {
  final Gson gson = new Gson();

  @Test
  @Order(1)
  void getAll() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/movies")
    .then()
        .statusCode(200);
  }

  @Test
  @Order(1)
  void getById() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/movies/1")
    .then()
        .statusCode(200);
  }

  @Test
  @Order(1)
  void getByIdKO() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/movies/50")
    .then()
        .statusCode(404);}

  @Test
  @Order(1)
  void getByTitle() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/movies/title/FirstMovie")
    .then()
        .statusCode(200);

  }

  @Test
  @Order(1)
  void getByTitleKO() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/movies/title/Teste")
    .then()
        .statusCode(404);
  }

  @Test
  @Order(2)
  void getByCountry() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/movies/country/Planet")
    .then()
        .statusCode(200);
  }

  @Test
  @Order(2)
  void getByCountryKO() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/movies/country/Test")
    .then()
        .statusCode(200);
  }

  @Test
  @Order(3)
  void create() {
    final String strTest = "teste";

    Movie movie = new Movie();
    movie.setCountry(strTest);
    movie.setDescription(strTest);
    movie.setDirector(strTest);
    movie.setTitle(strTest);
    movie.setId(null);

    given()
        .contentType(ContentType.JSON)
        .body(movie)
    .when()
        .post("/movies")
    .then()
        .statusCode(201);
  }

  @Test
  @Order(4)
  void updateById() {
    final String strTest = "teste";

    Movie movie = new Movie();
    movie.setCountry(strTest);
    movie.setDescription(strTest);
    movie.setDirector(strTest);
    movie.setTitle(strTest);
    movie.setId(1L);

    given()
        .contentType(ContentType.JSON)
        .body(movie)
    .when()
        .put("/movies/1")
    .then()
        .statusCode(200);

  }

  @Test
  @Order(4)
  void updateByIdKO() {
    final String strTest = "teste";

    Movie movie = new Movie();
    movie.setCountry(strTest);
    movie.setDescription(strTest);
    movie.setDirector(strTest);
    movie.setTitle(strTest);
    movie.setId(30L);

    given()
        .contentType(ContentType.JSON)
        .body(movie)
    .when()
        .put("/movies/30")
    .then()
        .statusCode(404);
  }

  @Test
  @Order(5)
  void deleteById() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .delete("/movies/1")
    .then()
        .statusCode(204);
  }

  @Test
  @Order(5)
  void deleteByIdKO() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .delete("/movies/10")
    .then()
        .statusCode(404);
    }
}
