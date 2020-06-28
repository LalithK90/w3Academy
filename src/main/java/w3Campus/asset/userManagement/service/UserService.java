package w3Campus.asset.userManagement.service;

import w3Campus.asset.employee.entity.Employee;
import w3Campus.asset.userManagement.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User persist(User user);

    boolean delete(Long id);

    List<User> search(User user);

    Long findByUserIdByUserName(String userName);

    User findByUserName(String name);

    User findUserByEmployee(Employee employee);

    boolean findByEmployee(Employee employee);
}
