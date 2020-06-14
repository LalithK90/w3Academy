package lk.w3Campus.asset.course.entity;

import lk.w3Campus.asset.course.entity.Enum.CourseEmployeeStatus;
import lk.w3Campus.asset.employee.entity.Employee;
import lk.w3Campus.util.audit.AuditEntity;
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
public class EmployeeBranchCourse extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private CourseEmployeeStatus courseEmployeeStatus;

    @ManyToOne
    private BranchCourse branchCourse;

    @ManyToOne
    private Employee employee;
}
