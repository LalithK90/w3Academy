package w3Campus.asset.course.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Course")
public class Course extends AuditEntity {

    @NotNull
    @Size(min=1,message = "You can not add course without name buddy...")
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "course")
    private List<CoursePrice> coursePrices;

    @OneToMany(mappedBy = "course")
    private List<BranchCourse> branchCourses;
}
