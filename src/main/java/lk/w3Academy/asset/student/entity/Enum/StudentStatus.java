package lk.w3Academy.asset.student.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StudentStatus {
    ACTIVE("Active"),
    BATCHDROP("Batch Drop"),
    COURSEDROP("Course Drop"),
    NOTCOMPLETE("Not Complete"),
    COMPLETED("Completed");

    private final String studentStatus;
}
