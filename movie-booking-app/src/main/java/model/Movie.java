package model;

public class Movie {
    private int id;
    private String name;
    private int duration;
    private String description;
    private double rating;
    private boolean isAvailable;

    // Constructor
    public Movie(int id, String name, int duration, String description, double rating, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.rating = rating;
        this.isAvailable = isAvailable;
    }

    // Overloaded constructor (defaults isAvailable to true)
    public Movie(int id, String name, String description, int duration, double rating) {
        this(id, name, duration, description, rating, true); // Available by default
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getDuration() { return duration; }
    public String getDescription() { return description; }
    public double getRating() { return rating; }
    public boolean isAvailable() { return isAvailable; }

    // Setter for id
    public void setId(int id) { this.id = id; }

    // Optional: orElse method for null handling
    public Movie orElse(Movie other) {
        return this != null ? this : other;
    }

    // Override toString for better object representation
    @Override
    public String toString() {
        return "Movie{id=" + id + ", name='" + name + "', duration=" + duration + ", description='" + description + "', rating=" + rating + ", isAvailable=" + isAvailable + "}";
    }

	/**
	 * @return
	 */
    public String getSynopsis() {
        return description != null ? description : "No synopsis available.";
    }


}


