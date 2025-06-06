/**
 * 
 */
package com.javaoop.gym_booking_app.service;

import model.Coach;
import com.javaoop.gym_booking_app.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
/**
 * 
 */
public class CoachService {
    private final CoachRepository coachRepository;

    @Autowired
    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    public Coach addCoach(Coach coach) {
        return coachRepository.save(coach);
    }

    public void deleteCoach(int id) {
        coachRepository.deleteById(id);
    }
    
	public Coach getCoachById(Long coachId) {
		// TODO Auto-generated method stub
		return null;
	}
}
