package w3Campus.asset.semester.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import w3Campus.asset.subject.entity.Result;
import w3Campus.asset.subject.entity.SubjectSemester;
import w3Campus.util.audit.AuditEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Semester")
public class Semester extends AuditEntity {

    private String code;

    private String name;

    @OneToMany(mappedBy = "semester")
    private List<Result> results;

    @OneToMany(mappedBy = "semester")
    private List<SubjectSemester> subjectSemesters;
}
