package org.gs;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class MovieRepositoryTest {

  @Inject
  MovieRepository movieRepository;

  @Test
  void findByCountryOK() {
    List<Movie> movies = movieRepository.findByCountry("Planet");
    assertEquals(2, movies.size());
  }

  @Test
  void findByCountryKO() {
    List<Movie> movies = movieRepository.findByCountry("Test");
    assertEquals(0, movies.size());
  }
}
