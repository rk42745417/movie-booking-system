/**
 * 
 */
package com.javaoop.gym_booking_app.controller;

import model.Coach;
import com.javaoop.gym_booking_app.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 */
public class CoachController {
    private final CoachService coachService;

    @Autowired
    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @PostMapping("/add")
    public Coach addCoach(@RequestBody Coach coach) {
        return coachService.addCoach(coach);
    }

    @GetMapping("/{coachId}")
    public Coach getCoach(@PathVariable Long coachId) {
        return coachService.getCoachById(coachId);
    }
}
