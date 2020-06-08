package lk.w3Academy.asset.subject.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Academy.asset.student.entity.SubjectResult;
import lk.w3Academy.asset.subject.entity.Enum.StreamLevel;
import lk.w3Academy.util.audit.AuditEntity;
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
    @Enumerated(EnumType.STRING)
    private StreamLevel streamLevel;

    @NotNull
    @Column(nullable = false,unique = true)
    private String indexNumber;

    @NotNull
    @Column(nullable = false,unique = true)
    private String year;

    @OneToMany
    private List<SubjectResult> subjectResults;

    @OneToMany(mappedBy = "result")
    private List<SubjectStudent> subjectStudents;

}
