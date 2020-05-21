package lk.w3Academy.asset.course.entity;

import lk.w3Academy.asset.branch.entity.Branch;
import lk.w3Academy.asset.employee.entity.Employee;
import lk.w3Academy.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchCourse extends AuditEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfEnd;

    @ManyToOne
    private CourseDetail courseDetail;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Branch branch;
}
