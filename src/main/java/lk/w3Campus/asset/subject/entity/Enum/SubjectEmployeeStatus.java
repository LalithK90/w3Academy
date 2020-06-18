package lk.w3Campus.asset.subject.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubjectEmployeeStatus {
    AC("Active"),
    DE("Deprecated"),
    CL("Closed");
    private final String subjectEmployeeStatus;
}
