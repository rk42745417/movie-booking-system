package dto;

import com.javaoop.gym_booking_app.model.CourseStatus;

/**
 * DTO for an update request.
 */
public record UpdateRequest(Long id, CourseStatus status) {
}