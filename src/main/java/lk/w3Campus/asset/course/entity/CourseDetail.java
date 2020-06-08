package lk.w3Campus.asset.course.entity;

import lk.w3Campus.asset.course.entity.Enum.CourseStatus;
import lk.w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
