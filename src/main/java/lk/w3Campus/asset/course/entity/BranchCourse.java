package lk.w3Campus.asset.course.entity;

import lk.w3Campus.asset.branch.entity.Branch;
import lk.w3Campus.asset.course.entity.Enum.CourseStatus;
import lk.w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchCourse extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfEnd;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Course course;

    @OneToMany
    private List<EmployeeBranchCourse> employeeBranchCourses;
}
