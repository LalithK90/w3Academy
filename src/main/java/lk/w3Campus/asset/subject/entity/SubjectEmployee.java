package lk.w3Campus.asset.subject.entity;

import lk.w3Campus.asset.employee.entity.Employee;
import lk.w3Campus.asset.subject.entity.Enum.EmployeeRoleSubject;
import lk.w3Campus.util.audit.AuditEntity;
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
public class SubjectEmployee extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private EmployeeRoleSubject employeeRoleSubject;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Subject subject;
}
