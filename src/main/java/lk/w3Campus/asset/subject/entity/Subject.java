package lk.w3Campus.asset.subject.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Campus.asset.semester.entity.Semester;
import lk.w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
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

    @OneToMany(mappedBy = "subject")
    private List<Semester> semesters;

    @OneToMany(mappedBy = "subject")
    private List<SubjectEmployee> subjectEmployees;

    @OneToMany(mappedBy = "subject")
    private List<SubjectStudent> subjectStudents;

}
