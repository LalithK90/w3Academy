package w3Campus.asset.student.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import w3Campus.asset.student.entity.Enum.Attempt;
import w3Campus.asset.student.entity.Enum.CompulsoryOLSubject;
import w3Campus.asset.student.entity.Enum.StreamLevel;
import w3Campus.asset.student.entity.Enum.SubjectResult;
import w3Campus.util.audit.AuditEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("StudentResult")
@ToString
public class StudentResult extends AuditEntity {

    private String indexNumber;

    private String subjectName;

    private String mark;

    private String year;

    @Enumerated(EnumType.STRING)
    private Attempt attempt;

    @Enumerated(EnumType.STRING)
    private StreamLevel streamLevel;

    @Enumerated(EnumType.STRING)
    private CompulsoryOLSubject compulsoryOLSubject;

    @Enumerated(EnumType.STRING)
    private SubjectResult subjectResult;

    @ManyToOne
    private Student student;

}
