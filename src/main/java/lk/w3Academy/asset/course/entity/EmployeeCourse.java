package lk.w3Academy.asset.course.entity;

import lk.w3Academy.asset.course.entity.Enum.CourseEmployeeStatus;
import lk.w3Academy.asset.employee.entity.Employee;
import lk.w3Academy.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCourse extends AuditEntity {
    @Enumerated(EnumType.STRING)
    private CourseEmployeeStatus courseEmployeeStatus;

    @ManyToOne
    private CourseDetail courseDetail;

    @ManyToOne
    private Employee employee;
}
