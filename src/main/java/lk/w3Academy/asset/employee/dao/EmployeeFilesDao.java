package lk.w3Academy.asset.employee.dao;

import lk.w3Academy.asset.employee.entity.Employee;
import lk.w3Academy.asset.employee.entity.EmployeeFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeFilesDao extends JpaRepository<EmployeeFiles, Long > {
    List< EmployeeFiles > findByEmployeeOrderByIdDesc(Employee employee);

    EmployeeFiles findByName(String filename);

    EmployeeFiles findByNewName(String filename);

    EmployeeFiles findByNewId(String filename);
}
