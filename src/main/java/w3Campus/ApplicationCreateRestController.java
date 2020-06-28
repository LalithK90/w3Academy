package w3Campus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import w3Campus.asset.commonAsset.model.Enum.CivilStatus;
import w3Campus.asset.commonAsset.model.Enum.Gender;
import w3Campus.asset.commonAsset.model.Enum.Title;
import w3Campus.asset.employee.entity.Employee;
import w3Campus.asset.employee.entity.Enum.Designation;
import w3Campus.asset.employee.entity.Enum.EmployeeStatus;
import w3Campus.asset.employee.service.EmployeeService;
import w3Campus.asset.userManagement.entity.Enum.UType;
import w3Campus.asset.userManagement.entity.Role;
import w3Campus.asset.userManagement.entity.User;
import w3Campus.asset.userManagement.service.RoleService;
import w3Campus.asset.userManagement.service.UserService;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
public class ApplicationCreateRestController {
    private final RoleService roleService;
    private final UserService userService;
    private final EmployeeService employeeService;


    @Autowired
    public ApplicationCreateRestController(RoleService roleService, UserService userService,
                                           EmployeeService employeeService) {
        this.roleService = roleService;
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @GetMapping("/select/user")
    public String saveUser() {
//roles list start
        String[] roles = {"ADMIN", "STUDENT"};
        for (String s : roles) {
            Role role = new Role();
            role.setRoleName(s);
            roleService.persist(role);
        }

//Employee
        Employee employee = new Employee();
        employee.setCode("W3E000000");
        employee.setName("Admin User");
        employee.setCallingName("Admin");
        employee.setName("908670000V");
        employee.setMobileOne("0750000000");
        employee.setTitle(Title.DR);
        employee.setGender(Gender.MALE);
        employee.setDesignation(Designation.NTM);
        employee.setCivilStatus(CivilStatus.UNMARRIED);
        employee.setEmployeeStatus(EmployeeStatus.WORKING);
        employee.setDateOfBirth(LocalDate.now().minusYears(18));
        employee.setDateOfAssignment(LocalDate.now());
        Employee employeeDb = employeeService.persist(employee);


        //admin user one
        User user = new User();
        user.setEmployee(employeeDb);
        user.setUsername("admin");
        user.setPassword("admin");
        String message = "Username:- " + user.getUsername() + "\n Password:- " + user.getPassword();
        user.setEnabled(true);
        user.setUType(UType.STAFF);
        user.setRoles(roleService.findAll()
                .stream()
                .filter(role -> role.getRoleName().equals("ADMIN"))
                .collect(Collectors.toList()));
        userService.persist(user);
// subject list
        String[] subjects = {"Mathematics","Science","Sinhala Language & Literature"
        ,"English Language & Literature","Religion","other"};
        return message;
    }


}
