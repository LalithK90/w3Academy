package lk.w3Campus.asset.subject.controller;


import lk.w3Campus.asset.employee.service.EmployeeService;
import lk.w3Campus.asset.semester.service.SemesterService;
import lk.w3Campus.asset.subject.entity.Enum.EmployeeRoleSubject;
import lk.w3Campus.asset.subject.entity.Subject;
import lk.w3Campus.asset.subject.service.SubjectService;
import lk.w3Campus.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/subject")
public class SubjectController implements AbstractController<Subject, Long> {
    private final SubjectService subjectService;
    private final SemesterService semesterService;
    private final EmployeeService employeeService;

    @Autowired
    public SubjectController(SubjectService subjectService, SemesterService semesterService, EmployeeService employeeService) {
        this.subjectService = subjectService;
        this.semesterService = semesterService;
        this.employeeService = employeeService;
    }

    private String commonThing(Model model, Subject subject) {
        model.addAttribute("addStatus", true);
        model.addAttribute("subject", subject);
        return "subject/subject";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return commonThing(model, new Subject());
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("subject", new Subject());
        return "subject/subject";
    }


    @GetMapping("/view/{id}")
    public String findById(@PathVariable Long id, Model model) {
        commonThing(model, new Subject());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("subjectDetail", subjectService.findById(id));
        return "subject/subject";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("addStatus", false);
        Subject subject = subjectService.findById(id);
        List<Subject> subjects = subjectService.findAll();
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i) == subject) {
                subjects.remove(i);
                break;
            }
        }
        model.addAttribute("subjects", subjects);
        model.addAttribute("subject", subject);
        return "subject/subject";
    }

    @PostMapping(value = {"/add", "/update"})
    public String persist(@Valid @ModelAttribute Subject subject, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, subject);
        }
        redirectAttributes.addFlashAttribute("subjectDetailShow", true);
        redirectAttributes.addFlashAttribute("subjectDetail", subjectService.persist(subject));
        return "redirect:/subject";
    }

    @GetMapping("/remove/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        subjectService.delete(id);
        model.addAttribute("message", "Successfully deleted");
        return "redirect:/subject";
    }

    @GetMapping("/employee/{id}")
    public String addEmployeeToSubject(@PathVariable Long id, Model model) {
        model.addAttribute("subject", subjectService.findById(id));
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("employeeRoleSubjects", EmployeeRoleSubject.values());
        return "redirect:/subject";
    }

}

