package lk.w3Academy.asset.course.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseStatus {
PENDING("Pending"),
    ONGOING("Ongoing"),
    SUSPEND("Suspend"),
    FINISHED("Finished");
private final String courseStatus;
}
