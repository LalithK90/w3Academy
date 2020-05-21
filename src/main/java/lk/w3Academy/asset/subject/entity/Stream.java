package lk.w3Academy.asset.subject.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Academy.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Stream")
public class Stream extends AuditEntity {

    private String name;

    @ManyToMany( fetch = FetchType.EAGER ,cascade = CascadeType.PERSIST)
    @Fetch( FetchMode.SUBSELECT )
    @JoinTable( name = "stream_subject",
            joinColumns = @JoinColumn( name = "stream_id" ),
            inverseJoinColumns = @JoinColumn( name = "subject_id" ) )
    private List<Subject> subjects;
}
