package dto;

/**
 * DTO for a reservation request.
 */
public class ReservationRequest {
    private Long courseId;
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}