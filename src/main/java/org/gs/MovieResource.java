package org.gs;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

  @Inject MovieRepository movieRepository;

  /**
   * Listar todos os filmes
   * @return {@link Response}
   */
  @GET
  public Response getAll() {
    List<Movie> movies = movieRepository.listAll();
    return Response.ok(movies).build();
  }

  /**
   * Recuperar filme pela Id
   * @param id {@link Long}
   * @return {@link Response}
   */
  @GET
  @Path("{id}")
  public Response getById(@PathParam("id") Long id) {
    return movieRepository
        .findByIdOptional(id)
        .map(movie -> Response.ok(movie).build())
        .orElse(Response.status(NOT_FOUND).build());
  }

  /**
   * Recuperar filme pelo titulo
   * @param title {@link String}
   * @return {@link Response}
   */
  @GET
  @Path("title/{title}")
  public Response getByTitle(@PathParam("title") String title) {
    return movieRepository
        .find("title", title)
        .singleResultOptional()
        .map(movie -> Response.ok(movie).build())
        .orElse(Response.status(NOT_FOUND).build());
  }

  /**
   * Recuperar filmes pelo pais
   * @param country {@link String}
   * @return {@link Response}
   */
  @GET
  @Path("country/{country}")
  public Response getByCountry(@PathParam("country") String country) {
    List<Movie> movies = movieRepository.findByCountry(country);
    return Response.ok(movies).build();
  }

  /**
   * Inserir filme
   * @param movie {@link Movie}
   * @return {@link Response}
   */
  @POST
  @Transactional
  public Response create(Movie movie) {
    movieRepository.persist(movie);
    if (movieRepository.isPersistent(movie)) {
      return Response.created(URI.create("/movies/" + movie.getId())).build();
    }
    return Response.status(BAD_REQUEST).build();
  }

  /**
   * Atualizar filme
   * @param id {@link Long}
   * @param movie {@link Movie}
   * @return {@link Response}
   */
  @PUT
  @Path("{id}")
  @Transactional
  public Response updateById(@PathParam("id") Long id, Movie movie) {
    return movieRepository
        .findByIdOptional(id)
        .map(
            m -> {
              m.setTitle(movie.getTitle());
              return Response.ok(m).build();
            })
        .orElse(Response.status(NOT_FOUND).build());
  }

  /**
   * Deletar filme
   * @param id {@link Long}
   * @return {@link Response}
   */
  @DELETE
  @Path("{id}")
  @Transactional
  public Response deleteById(@PathParam("id") Long id) {
    boolean deleted = movieRepository.deleteById(id);
    return deleted ? Response.noContent().build() : Response.status(NOT_FOUND).build();
  }
}
