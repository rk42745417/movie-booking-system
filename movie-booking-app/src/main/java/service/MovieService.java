package service;

import model.Movie;
import database.Database;
import java.util.*;
import java.util.stream.Collectors;

public class MovieService {
    public boolean addMovie(String name, String description, String rating) {
        String id = UUID.randomUUID().toString();
        Movie movie = new Movie(id, name, description, rating, true);
        Database.movies.put(id, movie);
        return true;
    }

    public boolean removeMovie(String movieId) {
        if (Database.movies.containsKey(movieId)) {
            Database.movies.remove(movieId);
            return true;
        }
        return false;
    }

    public List<Movie> getCurrentMovies() {
        return Database.movies.values().stream()
            .filter(Movie::isActive)
            .collect(Collectors.toList());
    }

    public String getMovieRating(String movieId) {
        Movie movie = Database.movies.get(movieId);
        return movie != null ? movie.rating : null;
    }
}
