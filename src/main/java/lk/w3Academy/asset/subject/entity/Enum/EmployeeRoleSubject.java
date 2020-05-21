package lk.w3Academy.asset.subject.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmployeeRoleSubject {
    RESOURCE("Resource Person"),
    SLECT("Senior Lecture"),
    JLECT("Junior Lecture"),
    DEMO("Demonstrator");

    private final String employeeRoleSubject;
}
