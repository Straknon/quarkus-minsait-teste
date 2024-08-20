package org.gs;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

@QuarkusTest
class MovieResourceTest {

  @InjectMock
  MovieRepository movieRepository;

  @Inject
  MovieResource movieResource;

  final long id = 0 ;
  final String strTest = "teste";
  final String strKoTest = "KO";

  @BeforeEach
  void setUp() {

    Movie movie = new Movie();
    movie.setCountry(strTest);
    movie.setDescription(strTest);
    movie.setDirector(strTest);
    movie.setTitle(strTest);
    movie.setId(id);

    Movie movieDois= new Movie();
    movie.setCountry(strTest);
    movie.setDescription(strTest);
    movie.setDirector(strTest);
    movie.setTitle(strTest);
    movie.setId(id+1);

    List<Movie> allMovies = new ArrayList<>();
    allMovies.add(movieDois);
    allMovies.add(movie);
    
    PanacheQuery<Movie> panacheQuery = Mockito.mock(PanacheQuery.class);
    Optional<Movie> opt = Optional.of(movie);
    when(movieRepository.listAll()).thenReturn(allMovies);
    when(movieRepository.findByIdOptional(id)).thenReturn(opt);

    when(movieRepository.find("title", strTest)).thenReturn(panacheQuery);
    when(movieRepository.find("title", strKoTest)).thenReturn(Mockito.mock(PanacheQuery.class));
    when(panacheQuery.singleResultOptional()).thenReturn(opt);

    when(movieRepository.findByCountry(strTest)).thenReturn(allMovies);
    
    when(movieRepository.deleteById(id)).thenReturn(true);
  }

  @Test
  void getAll() {
    Response res = movieResource.getAll();
    try {
      List<Movie> movies = (List<Movie>) res.getEntity();
      assertEquals(2, movies.size());
      assertEquals(200, res.getStatus());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getByIdOK() {
    Response res = movieResource.getById(id);
    try {
      Movie movie = (Movie) res.getEntity();
      assertEquals(200, res.getStatus());
      assertNotNull(movie);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getByIdKO() {
    Response res = movieResource.getById(1L);
    try {
      Movie movie = (Movie) res.getEntity();
      assertEquals(404, res.getStatus());
      assertNull(movie);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getByTitleOK() {
    Response res = movieResource.getByTitle(strTest);
    try {
      Movie movie = (Movie) res.getEntity();
      assertEquals(200, res.getStatus());
      assertNotNull(movie);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getByTitleKO() {
    Response res = movieResource.getByTitle(strKoTest);
    try {
      Movie movie = (Movie) res.getEntity();
      assertEquals(404, res.getStatus());
      assertNull(movie);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getByCountry() {
    Response res = movieResource.getByCountry(strTest);
    try {
      List<Movie> movies = (List<Movie>) res.getEntity();
      assertEquals(2, movies.size());
      assertEquals(200, res.getStatus());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void createOK() {
    
    Movie movie = new Movie();
    movie.setCountry(strTest);
    movie.setDescription(strTest);
    movie.setDirector(strTest);
    movie.setTitle(strTest);
    movie.setId(id);

    when(movieRepository.isPersistent(movie)).thenReturn(true);

    Response res = movieResource.create(movie);
    try {
      assertEquals(201, res.getStatus());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void createKO() {
    
    Movie movie = new Movie();
    movie.setCountry(strTest);
    movie.setDescription(strTest);
    movie.setDirector(strTest);
    movie.setTitle(strTest);
    movie.setId(id);

    Response res = movieResource.create(movie);
    try {
      assertEquals(400, res.getStatus());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void updateByIdOK() {
    Movie movie = new Movie();
    movie.setCountry(strTest);
    movie.setDescription(strTest);
    movie.setDirector(strTest);
    movie.setTitle(strTest);
    movie.setId(id);

    Response res = movieResource.updateById(id,movie);
    try {
      assertEquals(200, res.getStatus());
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }

  @Test
  void updateByIdKO() {
    Movie movie = new Movie();
    movie.setCountry(strTest);
    movie.setDescription(strTest);
    movie.setDirector(strTest);
    movie.setTitle(strTest);
    movie.setId(id);

    Response res = movieResource.updateById(1L,movie);
    try {
      assertEquals(404, res.getStatus());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void deleteByIdOK() {
    Response res = movieResource.deleteById(id);
    try {
      assertEquals(204, res.getStatus());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void deleteByIdKO() {
    Response res = movieResource.deleteById(1L);
    try {
      assertEquals(404, res.getStatus());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
