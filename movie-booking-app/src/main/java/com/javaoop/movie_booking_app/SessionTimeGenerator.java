package com.javaoop.movie_booking_app;

import java.time.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;

public class SessionTimeGenerator {

    static class Movie {
        Long movieId;
        String title;
        int durationMinutes;

        public Movie(Long movieId, String title, int durationMinutes) {
            this.movieId = movieId;
            this.title = title;
            this.durationMinutes = durationMinutes;
        }

        // getters needed for Jackson if fields are private (optional if fields public)
        public Long getMovieId() { return movieId; }
        public String getTitle() { return title; }
        public int getDurationMinutes() { return durationMinutes; }
    }

    static class Session {
        Long showtimeId;
        Long movieId;
        String title;
        LocalDateTime startTime;
        LocalDateTime endTime;
        String hallType;

        public Session(Long showtimeId, Long movieId, String title,
                       LocalDateTime startTime, LocalDateTime endTime, String hallType) {
            this.showtimeId = showtimeId;
            this.movieId = movieId;
            this.title = title;
            this.startTime = startTime;
            this.endTime = endTime;
            this.hallType = hallType;
        }

        public Long getShowtimeId() { return showtimeId; }
        public Long getMovieId() { return movieId; }
        public String getTitle() { return title; }
        public LocalDateTime getStartTime() { return startTime; }
        public LocalDateTime getEndTime() { return endTime; }
        public String getHallType() { return hallType; }
    }

    public static void main(String[] args) throws Exception {
        List<Movie> movies = Arrays.asList(
            new Movie(1L, "美國隊長：無畏新世界", 131),
            new Movie(2L, "機動戰士Gundam GquuuuuuX -Beginning-", 81),
            new Movie(3L, "夜校女生", 109),
            new Movie(4L, "史蒂芬金之猴子", 98),
            new Movie(5L, "殺人預言", 99),
            new Movie(6L, "窒息倒數", 93),
            new Movie(7L, "火線追緝令", 126),
            new Movie(8L, "粗獷派建築師", 215)
        );

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6); // 7 days total
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(17, 0);

        List<Session> sessions = new ArrayList<>();
        long sessionIdCounter = 1;

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            for (Movie movie : movies) {
                LocalTime timeCursor = openTime;
                while (!timeCursor.plusMinutes(movie.durationMinutes).isAfter(closeTime)) {
                    String hallType = (movie.movieId % 2 == 0) ? "big_room" : "small_room";

                    LocalDateTime startDateTime = LocalDateTime.of(date, timeCursor);
                    LocalDateTime endDateTime = startDateTime.plusMinutes(movie.durationMinutes);

                    sessions.add(new Session(sessionIdCounter++, movie.movieId, movie.title, startDateTime, endDateTime, hallType));

                    // Move time cursor forward by movie duration + 15 mins cleaning time
                    timeCursor = timeCursor.plusMinutes(movie.durationMinutes + 15);

                    // Align to nearest :00 or :30
                    int minute = timeCursor.getMinute();
                    if (minute != 0 && minute != 30) {
                        if (minute < 30) {
                            timeCursor = timeCursor.withMinute(30);
                        } else {
                            timeCursor = timeCursor.plusHours(1).withMinute(0);
                        }
                    }
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules(); // for Java time support
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        System.out.println("Generated session count: " + sessions.size());

        File outputFile = new File("src/main/resources/session_time.json");
        outputFile.getParentFile().mkdirs();
        mapper.writeValue(outputFile, sessions);
        System.out.println("session-time.json generated at " + outputFile.getAbsolutePath());
    }
}

