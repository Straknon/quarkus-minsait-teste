package org.gs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MovieTest {

    @Test
    void classTest(){
        final String strTest = "Teste";

        Movie movie = new Movie();
        movie.setCountry(strTest);
        movie.setDescription(strTest);
        movie.setDirector(strTest);
        movie.setTitle(strTest);
        movie.setId(0L);

        Assertions.assertNotNull(movie.getCountry());
        Assertions.assertNotNull(movie.getDescription());
        Assertions.assertNotNull(movie.getDirector());
        Assertions.assertNotNull(movie.getId());
        Assertions.assertNotNull(movie.getTitle());
    }
}
