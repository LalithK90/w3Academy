package lk.w3Academy.asset.employee.dao;

import lk.w3Academy.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    Employee findFirstByOrderByIdDesc();

    Employee findByNic(String nic);
}
