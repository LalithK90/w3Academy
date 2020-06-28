package w3Campus.asset.subject.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import w3Campus.asset.semester.entity.Semester;
import w3Campus.asset.subject.entity.Enum.SubjectEmployeeStatus;
import w3Campus.util.audit.AuditEntity;
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
@JsonFilter("SubjectSemester")
public class SubjectSemester extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private SubjectEmployeeStatus subjectEmployeeStatus;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Semester semester;

}
