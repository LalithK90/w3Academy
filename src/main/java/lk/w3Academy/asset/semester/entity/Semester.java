package lk.w3Academy.asset.semester.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Academy.asset.subject.entity.Subject;
import lk.w3Academy.util.audit.AuditEntity;
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
@JsonFilter("Semester")
public class Semester extends AuditEntity {
    private String name;

    @ManyToOne
    private Subject subject;
}
