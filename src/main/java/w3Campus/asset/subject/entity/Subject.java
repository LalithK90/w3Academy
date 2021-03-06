package w3Campus.asset.subject.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Subject")
public class Subject extends AuditEntity {
    @NotNull
    @Column(unique = true, nullable = false)
    private String code, name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "subject", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    private List<SubjectSemester> subjectSemesters;

    @OneToMany(mappedBy = "subject")
    private List<SubjectEmployee> subjectEmployees;

    @OneToMany(mappedBy = "subject")
    private List<SubjectStudent> subjectStudents;

}
