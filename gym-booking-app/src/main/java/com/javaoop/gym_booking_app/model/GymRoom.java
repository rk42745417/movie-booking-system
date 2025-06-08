package com.javaoop.gym_booking_app.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

/**
 * Represents a room or location in the gym where courses are held.
 * This is the full entity based on the provided schema.
 */
@Entity
@Table(name = "gym_room")
public class GymRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name; // e.g., "Hall A", "Yoga Studio"

    @Column(name = "location")
    private String location; // e.g., "3rd Floor", "B1"

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    /**
     * A list of main equipment available in the room.
     * JPA will store this in a separate table (e.g., gym_room_equipment)
     * linking back to the gym_room's ID.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "gym_room_equipment", joinColumns = @JoinColumn(name = "gym_room_id"))
    @Column(name = "equipment_name", nullable = false)
    private List<String> equipment = new ArrayList<>();


    // --- Constructors, Getters, and Setters ---

    public GymRoom() {
    }

    // Getters and Setters would be included here...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }
}