package lk.w3Campus.asset.employee.controller;

import lk.w3Campus.asset.branch.service.BranchService;
import lk.w3Campus.asset.commonAsset.service.CommonService;
import lk.w3Campus.asset.employee.entity.Employee;
import lk.w3Campus.asset.employee.entity.EmployeeFiles;
import lk.w3Campus.asset.employee.entity.Enum.EmployeeStatus;
import lk.w3Campus.asset.employee.service.EmployeeFilesService;
import lk.w3Campus.asset.employee.service.EmployeeService;
import lk.w3Campus.asset.userManagement.entity.User;
import lk.w3Campus.asset.userManagement.service.UserService;
import lk.w3Campus.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.lang.Integer.valueOf;

@RequestMapping("/employee")
@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeFilesService employeeFilesService;
    private final DateTimeAgeService dateTimeAgeService;
    private final CommonService commonService;
    private final UserService userService;
    private final BranchService branchService;

    //todo current loggin -> user Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeFilesService employeeFilesService,
                              DateTimeAgeService dateTimeAgeService, CommonService commonService,
                              UserService userService, BranchService branchService) {
        this.employeeService = employeeService;
        this.employeeFilesService = employeeFilesService;

        this.dateTimeAgeService = dateTimeAgeService;
        this.commonService = commonService;
        this.userService = userService;
        this.branchService = branchService;
    }
//----> Employee details management - start <----//

    // Common things for an employee add and update
    private String commonThings(Model model) {
        commonService.commonEmployeeAndStudent(model);
        model.addAttribute("branches", branchService.findAll());
        return "employee/addEmployee";
    }

    //When scr called file will send to
    @GetMapping("/file/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("filename") String filename) {
        EmployeeFiles file = employeeFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    //Send all employee data
    @RequestMapping
    public String employeePage(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employee/employee";
    }

    //Send on employee details
    @GetMapping(value = "/{id}")
    public String employeeView(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employeeDetail", employee);
        model.addAttribute("addStatus", false);
        model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
        return "employee/employee-detail";
    }

    //Send employee data edit
    @GetMapping(value = "/edit/{id}")
    public String editEmployeeForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("newEmployee", employee.getNumber());
        model.addAttribute("addStatus", false);
        model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
        return commonThings(model);
    }

    //Send an employee add form
    @GetMapping(value = {"/add"})
    public String employeeAddForm(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("employee", new Employee());
        return commonThings(model);
    }

    //Employee add and update
    @PostMapping(value = {"/add", "/update"})
    public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("employee", employee);
            return commonThings(model);
        }
        try {
            employee.setMobileOne(commonService.commonMobileNumberLengthValidator(employee.getMobileOne()));
            employee.setMobileTwo(commonService.commonMobileNumberLengthValidator(employee.getMobileTwo()));
            employee.setLand(commonService.commonMobileNumberLengthValidator(employee.getLand()));
            //if new employee need to assign employee number
            if (employee.getId() == null) {
                String newEmployeeNumber, input;
                String code = "W3E";
                //last employee from database
                Employee lastEmployee = employeeService.lastEmployee();
                if (lastEmployee != null) {
                    input = lastEmployee.getNumber();
                    int employeeNumber =
                            Integer.parseInt(input.replaceAll("[^0-9]+", "")) + 1;
                    newEmployeeNumber = commonService.numberIncrement(employeeNumber, code);

                } else {
                    newEmployeeNumber = commonService.numberIncrement(0, code);
                }
                employee.setNumber(newEmployeeNumber);
            }

            //after save employee files and save employee
            Employee savedEmployee = employeeService.persist(employee);

            //if employee state is not working he or she cannot access to the system
            if (!employee.getEmployeeStatus().equals(EmployeeStatus.WORKING)) {
                User user = userService.findUserByEmployee(employeeService.findByNic(employee.getNic()));
                //if employee not a user
                if (user != null) {
                    user.setEnabled(false);
                    userService.persist(user);
                }
            }
            //save employee images file
            if (employee.getFile().getOriginalFilename() != null) {
                EmployeeFiles employeeFiles = employeeFilesService.findByEmployee(savedEmployee);
                if (employeeFiles != null) {
                    // update new contents
                    employeeFiles.setPic(employee.getFile().getBytes());
                    // Save all to database
                } else {
                    employeeFiles = new EmployeeFiles(employee.getFile().getOriginalFilename(),
                            employee.getFile().getContentType(),
                            employee.getFile().getBytes(),
                            employee.getNic().concat("-" + LocalDateTime.now()),
                            UUID.randomUUID().toString().concat("employee"));
                    employeeFiles.setEmployee(employee);
                }
                employeeFilesService.persist(employeeFiles);
            }
            return "redirect:/employee";

        } catch (Exception e) {
            ObjectError error = new ObjectError("employee",
                    "There is already in the system. <br>System message -->" + e.toString());
            result.addError(error);
            model.addAttribute("addStatus", true);
            model.addAttribute("employee", employee);
            return commonThings(model);
        }
    }

    //If need to employee {but not applicable for this }
    @GetMapping(value = "/remove/{id}")
    public String removeEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }

    //To search employee any giving employee parameter
    @GetMapping(value = "/search")
    public String search(Model model, Employee employee) {
        model.addAttribute("employeeDetail", employeeService.search(employee));
        return "employee/employee-detail";
    }
}