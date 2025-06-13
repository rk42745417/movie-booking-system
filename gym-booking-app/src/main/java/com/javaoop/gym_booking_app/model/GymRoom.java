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


    /**
     * Default constructor.
     */
    public GymRoom() {
    }


    /**
     * Gets the ID of the gym room.
     * @return The ID of the gym room.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the gym room.
     * @param id The new ID of the gym room.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the gym room.
     * @return The name of the gym room.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the gym room.
     * @param name The new name of the gym room.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the location of the gym room.
     * @return The location of the gym room.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the gym room.
     * @param location The new location of the gym room.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the capacity of the gym room.
     * @return The capacity of the gym room.
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the gym room.
     * @param capacity The new capacity of the gym room.
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the equipment in the gym room.
     * @return The equipment in the gym room.
     */
    public List<String> getEquipment() {
        return equipment;
    }

    /**
     * Sets the equipment in the gym room.
     * @param equipment The new equipment in the gym room.
     */
    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }
}