/**
 * 
 */
package service;

import model.Showtime;
import repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    public Showtime getShowtimeById(int id) {
        return showtimeRepository.findById(id).orElse(null);
    }

    public Showtime addShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }
}

