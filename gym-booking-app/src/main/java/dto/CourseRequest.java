package dto;

import com.javaoop.gym_booking_app.model.CourseStatus;
import java.time.LocalDateTime;
import java.util.List;

public record CourseRequest(
    String coachName,
    String title,
    String description,
    Integer capacity,
    LocalDateTime startTime,
    LocalDateTime endTime,
    CourseStatus status,
    List<String> tags,
    Long roomId
) {}