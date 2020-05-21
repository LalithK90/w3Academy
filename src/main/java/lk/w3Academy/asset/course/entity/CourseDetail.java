package lk.w3Academy.asset.course.entity;

import lk.w3Academy.asset.branch.entity.Branch;
import lk.w3Academy.asset.course.entity.Enum.CourseEmployeeStatus;
import lk.w3Academy.asset.course.entity.Enum.CourseStatus;
import lk.w3Academy.asset.employee.entity.Employee;
import lk.w3Academy.util.audit.AuditEntity;
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
public class CourseDetail extends AuditEntity {


    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;



    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "courseDetail")
    private List<EmployeeCourse> employeeCourses;

    @OneToMany(mappedBy = "courseDetail")
    private List<BranchCourse> branchCourses;

}
