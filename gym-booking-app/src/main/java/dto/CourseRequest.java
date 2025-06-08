package dto;

import com.javaoop.gym_booking_app.model.CourseStatus;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 前端「新增課程」的請求物件  
 * - 已移除 coachName、roomId
 */
public record CourseRequest(
        String           title,
        String           description,
        Integer          capacity,
        LocalDateTime    startTime,
        LocalDateTime    endTime,
        CourseStatus     status,
        List<String>     tags
) {}