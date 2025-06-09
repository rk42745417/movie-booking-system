package dto;

import com.javaoop.gym_booking_app.model.CourseStatus;

public record UpdateRequest(Long id, CourseStatus status) {
}
