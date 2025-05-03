package model;

import java.time.LocalDateTime;

public class Session {
    private int id;
    private int movieId;
    private int theaterId;
    private LocalDateTime time;

    public Session(int id, int movieId, int theaterId, LocalDateTime time) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.time = time;
    }

    public int getId() { return id; }
    public int getMovieId() { return movieId; }
    public int getTheaterId() { return theaterId; }
    public LocalDateTime getTime() { return time; }
}

