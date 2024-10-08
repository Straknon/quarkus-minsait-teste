package org.gs;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {

  /**
   * Encontrar filmes por pais
   * @param country
   * @return {@link List}
   */
  public List<Movie> findByCountry(String country) {
    return list("SELECT m FROM Movie m WHERE m.country = ?1 ORDER BY id DESC", country);
  }
}
