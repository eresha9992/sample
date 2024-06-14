package com.java.aforv.serviceImpl;

import com.java.aforv.model.Movie;
import com.java.aforv.repo.MovieRepository;
import com.java.aforv.service.MovieService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie create(Movie movie) {
        System.out.println(movie);
        return movieRepository.save(movie);
    }

    @Override
    public Movie read(Long id) {

        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException(" movie not found"));
    }

    @Override
    public Movie update(Long id, Movie movie) {
        Movie existingMovie=movieRepository.findById(id).orElse(null);
        existingMovie.setName(movie.getName());
        existingMovie.setDirector(movie.getDirector());
        existingMovie.setActors(movie.getActors());
        return movieRepository.save(existingMovie);
    }


        @Override
    public void delete(Long id) {
         movieRepository.deleteById(id);

    }
}







