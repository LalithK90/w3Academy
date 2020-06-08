package lk.w3Academy.asset.subject.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Academy.asset.student.entity.Student;
import lk.w3Academy.asset.subject.entity.Enum.SitedTime;
import lk.w3Academy.util.audit.AuditEntity;
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
@JsonFilter("SubjectStudent")
public class SubjectStudent extends AuditEntity {

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Result result;

    @Enumerated(EnumType.STRING)
    private SitedTime sitedTime;

}
