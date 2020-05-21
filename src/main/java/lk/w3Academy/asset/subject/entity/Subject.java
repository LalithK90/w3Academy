package lk.w3Academy.asset.subject.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Academy.asset.semester.entity.Semester;
import lk.w3Academy.asset.student.entity.Student;
import lk.w3Academy.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate classDate;

    @OneToMany(mappedBy = "subject")
    private List<Semester> semesters;

    @OneToMany(mappedBy = "subject")
    private List<SubjectEmployee> subjectEmployees;

    @ManyToMany( fetch = FetchType.EAGER ,cascade = CascadeType.PERSIST)
    @Fetch( FetchMode.SUBSELECT )
    @JoinTable( name = "subject_student",
            joinColumns = @JoinColumn( name = "subject_id" ),
            inverseJoinColumns = @JoinColumn( name = "student_id" ) )
    private List<Student> students;

}
