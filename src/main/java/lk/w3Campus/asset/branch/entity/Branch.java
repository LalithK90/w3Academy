package lk.w3Campus.asset.branch.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Campus.asset.branch.entity.Enum.BranchStatus;
import lk.w3Campus.asset.course.entity.BranchCourse;
import lk.w3Campus.asset.employee.entity.Employee;
import lk.w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Branch")
public class Branch extends AuditEntity {

    @Size(min = 5, message = "Your name cannot be accepted")
    @NotNull
    private String name;

    @Column(unique = true, nullable = false)
    @NotNull
    private String code;

    private String mobile;

    private String land;

    private String address;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private BranchStatus branchStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @OneToMany(mappedBy = "branch")
    private List<Employee> employees;

    @OneToMany(mappedBy = "branch")
    private List<BranchCourse> branchCourses;
}
