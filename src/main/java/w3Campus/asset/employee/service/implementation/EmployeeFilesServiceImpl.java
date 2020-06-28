package w3Campus.asset.employee.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import w3Campus.asset.commonAsset.model.FileInfo;
import w3Campus.asset.employee.controller.EmployeeController;
import w3Campus.asset.employee.dao.EmployeeFilesDao;
import w3Campus.asset.employee.entity.Employee;
import w3Campus.asset.employee.entity.EmployeeFiles;
import w3Campus.asset.employee.service.EmployeeFilesService;

import java.util.List;

@Service
@CacheConfig(cacheNames = "employeeFiles")
public class EmployeeFilesServiceImpl implements EmployeeFilesService {
    private final EmployeeFilesDao employeeFilesDao;

    @Autowired
    public EmployeeFilesServiceImpl(EmployeeFilesDao employeeFilesDao) {
        this.employeeFilesDao = employeeFilesDao;
    }

    public EmployeeFiles findByName(String filename) {
        return employeeFilesDao.findByName(filename);
    }

    public void persist(EmployeeFiles storedFile) {
        employeeFilesDao.save(storedFile);
    }


    public List<EmployeeFiles> search(EmployeeFiles employeeFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<EmployeeFiles> employeeFilesExample = Example.of(employeeFiles, matcher);
        return employeeFilesDao.findAll(employeeFilesExample);
    }

    public EmployeeFiles findById(Long id) {
        return employeeFilesDao.getOne(id);
    }

    public EmployeeFiles findByNewID(String filename) {
        return employeeFilesDao.findByNewId(filename);
    }

    @Cacheable
    public FileInfo employeeFileDownloadLink(Employee employee) {
        EmployeeFiles employeeFiles = employeeFilesDao.findByEmployee(employee);
        if (employeeFiles != null) {
            return new FileInfo(employeeFiles.getName(), employeeFiles.getCreatedAt(), MvcUriComponentsBuilder
                    .fromMethodName(EmployeeController.class, "downloadFile", employeeFiles.getNewId())
                    .build()
                    .toString());
        } else {
            return null;
        }
    }


    public EmployeeFiles findByEmployee(Employee employee) {
        return employeeFilesDao.findByEmployee(employee);
    }

}
