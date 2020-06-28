package w3Campus.asset.subject.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import w3Campus.asset.student.entity.Student;
import w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
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

}
