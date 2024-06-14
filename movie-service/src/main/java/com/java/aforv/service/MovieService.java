package com.java.aforv.service;

import com.java.aforv.MovieServiceApplication;
import com.java.aforv.model.Movie;

public interface MovieService {

    public Movie create(Movie movie);

    public Movie read(Long id);

    public Movie update(Long id,Movie movie);
    public void delete(Long id);
}
