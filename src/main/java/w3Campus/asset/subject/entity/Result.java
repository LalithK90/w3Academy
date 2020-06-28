package w3Campus.asset.subject.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import w3Campus.asset.semester.entity.Semester;
import w3Campus.asset.student.entity.Enum.StreamLevel;
import w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Result")
public class Result extends AuditEntity {

    @NotNull
    @Column(nullable = false,unique = true)
    private String indexNumber;

    @NotNull
    @Column(nullable = false,unique = true)
    private String year;

    @Enumerated(EnumType.STRING)
    private StreamLevel streamLevel;

    @ManyToOne
    private Semester semester;

    @OneToMany(mappedBy = "result")
    private List<SubjectStudent> subjectStudents;

}
