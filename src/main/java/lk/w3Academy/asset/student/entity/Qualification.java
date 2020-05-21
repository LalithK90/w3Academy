package lk.w3Academy.asset.student.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Academy.asset.subject.entity.Enum.StreamLevel;
import lk.w3Academy.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Qualification")
public class Qualification extends AuditEntity {
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "student_qualification",
            joinColumns = @JoinColumn(name = "qualification_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

}
