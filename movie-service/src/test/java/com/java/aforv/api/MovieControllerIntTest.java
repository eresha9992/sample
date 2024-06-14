package com.java.aforv.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.aforv.model.Movie;
import com.java.aforv.repo.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void cleanUp() {
        movieRepository.deleteAllInBatch();
    }

    @Test
    void givenMovie_whenCreateMovie_thenReturnSaveMovie() throws Exception {

        // Given
        Movie movie = new Movie();
        movie.setName("rrr");
        movie.setDirector("Rajawali");
        movie.setActors(List.of("ntr", "Ramacharan", "alibhat"));

        // When
        var response = mockMvc.perform(post("/movies/create movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue())) // Use notNullValue() without is()
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }

    @Test
    void givenMovieId_whenFetchMovie_thenReturnMovie() throws Exception {
        // Given
        Movie movie = new Movie();
        movie.setName("rrr");
        movie.setDirector("Rajawali");
        movie.setActors(List.of("ntr", "Ramacharan", "alibhat"));
        Movie savedMovie = movieRepository.save(movie);

        // When
        var response = mockMvc.perform(get("/movies/" + savedMovie.getId()));

        // Then verify saved movie
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedMovie.getId().intValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }
    @Test
    void givenSavedMovie_whenUpdateMovie_thenUpdateInDb() throws Exception {
        //Given
        Movie movie=new Movie();
        movie.setName("rrr");
        movie.setDirector("Rajawali");
        movie.setActors(List.of("ntr", "Ramacharan", "alibhat"));
        Movie savedMovie = movieRepository.save(movie);
        Long id=savedMovie.getId();
        //update Movie
        movie.setActors(List.of("ntr", "Ramacharan", "alibhat","ajaydevagan"));

        //when
        var response=mockMvc.perform(put("/movies/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));
        //verify updated movie
        response.andDo(print())
                .andExpect(status().isOk());
         var fetchResponse=mockMvc.perform(get("/movies/"+id));

         fetchResponse.andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.name",is(movie.getName())))
                 .andExpect(jsonPath("$.director",is(movie.getDirector())))
                 .andExpect(jsonPath("$.actors",is(movie.getActors())));


    }
    @Test
    void givenMovie_whenDeleteRequest_thenMovieRemovefromDb() throws  Exception{
        //Given
        Movie movie=new Movie();
        movie.setName("rrr");
        movie.setDirector("Rajawali");
        movie.setActors(List.of("ntr", "Ramacharan", "alibhat"));
        Movie savedMovie = movieRepository.save(movie);
        Long id=savedMovie.getId();

        //when
        mockMvc.perform(delete("/movies/"+id))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(movieRepository.findById(id).isPresent());

    }



}
