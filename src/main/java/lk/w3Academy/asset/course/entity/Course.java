package lk.w3Academy.asset.course.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Academy.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Course")
public class Course extends AuditEntity {

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    private CoursePrice coursePrice;
}
