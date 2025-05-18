package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.User;
import com.javaoop.movie_booking_app.model.Booking;
import com.javaoop.movie_booking_app.repository.MovieRepository;
import com.javaoop.movie_booking_app.repository.UserRepository;
import com.javaoop.movie_booking_app.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public AdminService(MovieRepository movieRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void deleteBooking(int id) {
        bookingRepository.deleteById(id);
    }
}


