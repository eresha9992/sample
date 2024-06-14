package com.java.aforv.api;

import com.java.aforv.model.Movie;
import com.java.aforv.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {
    @Autowired
    private MovieService movieService;


    @PostMapping("/create movie")
    public ResponseEntity<Movie> CreateMovie(@RequestBody Movie movie){

        Movie createdMovie=movieService.create(movie);
        log.info("Returned  save movie with id:{}",createdMovie.getId());


        return ResponseEntity.ok(createdMovie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie>getMovie(@PathVariable Long id) {
        Movie getMovie=movieService.read(id);
        log.info("Returned get movie with id:{}",id);
        return ResponseEntity.ok(getMovie);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateSkillsDetails(@PathVariable Long id,@RequestBody Movie movie) {
        Movie savedMovie= movieService.update(id, movie);
        log.info("Returned  update movie with id:{}",id);
        return new ResponseEntity<>(savedMovie, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public  void deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
        log.info("Returned  delete movie with id:{}",id);
    }

}
