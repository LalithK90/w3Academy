package lk.w3Academy.asset.employee.service;

import lk.w3Academy.asset.commonAsset.model.FileInfo;
import lk.w3Academy.asset.employee.entity.Employee;
import lk.w3Academy.asset.employee.entity.EmployeeFiles;

import java.util.List;

public interface EmployeeFilesService {
    EmployeeFiles findByName(String filename);
    void persist(EmployeeFiles storedFile);
    List< EmployeeFiles > search(EmployeeFiles employeeFiles);
    EmployeeFiles findById(Long id);
    EmployeeFiles findByNewID(String filename);
    List<FileInfo> employeeFileDownloadLinks(Employee employee);
    EmployeeFiles findByEmployee(Employee employee);

}
