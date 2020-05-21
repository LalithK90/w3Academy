package lk.w3Academy.asset.student.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Academy.asset.student.entity.Enum.Attempt;
import lk.w3Academy.asset.student.entity.Enum.SubResult;
import lk.w3Academy.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("SubjectResult")
public class SubjectResult extends AuditEntity {
    @Enumerated(EnumType.STRING)
    private SubResult subResult;

    @Enumerated(EnumType.STRING)
    private Attempt attempt;
}
