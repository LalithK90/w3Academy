package lk.w3Academy.asset.employee.service;

import lk.w3Academy.asset.employee.dao.EmployeeDao;
import lk.w3Academy.asset.employee.entity.Employee;
import lk.w3Academy.asset.userManagement.dao.UserDao;
import lk.w3Academy.asset.userManagement.entity.Enum.UType;
import lk.w3Academy.asset.userManagement.entity.User;
import lk.w3Academy.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "employee")
public class EmployeeService implements AbstractService<Employee, Long> {

    private final EmployeeDao employeeDao;
    private final UserDao userDao;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao, UserDao userDao) {
        this.employeeDao = employeeDao;
        this.userDao = userDao;
    }

    @Cacheable
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Cacheable
    public Employee findById(Long id) {
        return employeeDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "employee", allEntries = true)},
            put = {@CachePut(value = "employee", key = "#employee.id")})
    @Transactional
    public Employee persist(Employee employee) {
        if (employee.getId() == null) {
            Employee employee1 = employeeDao.save(employee);
            User user = userDao.findByUsername(employee.getEmail());
            user.setEmployee(employee1);
            user.setUType(UType.STAFF);
            userDao.save(user);
            return employee1;
        }
        return employeeDao.save(employee);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        employeeDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<Employee> search(Employee employee) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Employee> employeeExample = Example.of(employee, matcher);
        return employeeDao.findAll(employeeExample);
    }

    public boolean isEmployeePresent(Employee employee) {
        return employeeDao.equals(employee);
    }


    public Employee lastEmployee() {
        return employeeDao.findFirstByOrderByIdDesc();
    }

    @Cacheable
    public Employee findByNic(String nic) {
        return employeeDao.findByNic(nic);
    }
}
