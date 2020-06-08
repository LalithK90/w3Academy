package lk.w3Campus.asset.course.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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
