package lk.w3Campus.asset.semester.controller;


import lk.w3Campus.asset.semester.entity.Semester;
import lk.w3Campus.asset.semester.service.SemesterService;
import lk.w3Campus.asset.subject.entity.Enum.SubjectEmployeeStatus;
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
@RequestMapping("/semester")
public class SemesterController implements AbstractController<Semester, Long> {
    private final SemesterService semesterService;

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    private String commonThing(Model model, Semester semester) {
        model.addAttribute("addStatus", true);
        model.addAttribute("semester", semester);
        return "semester/semester";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("semesters", semesterService.findAll());
        return commonThing(model, new Semester());
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("semester", new Semester());
        return "semester/semester";
    }


    @GetMapping("/view/{id}")
    public String findById(@PathVariable Long id, Model model) {
        commonThing(model, new Semester());
        model.addAttribute("semesters", semesterService.findAll());
        model.addAttribute("semesterDetail", semesterService.findById(id));
        return "semester/semester";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("addStatus", false);
        Semester semester = semesterService.findById(id);
        List<Semester> semesters = semesterService.findAll();
        for (int i = 0; i < semesters.size(); i++) {
            if (semesters.get(i) == semester) {
                semesters.remove(i);
                break;
            }
        }
        model.addAttribute("semesters", semesters);
        model.addAttribute("semester", semester);
        return "semester/semester";
    }

    @PostMapping(value = {"/add", "/update"})
    public String persist(@Valid @ModelAttribute Semester semester, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, semester);
        }
        redirectAttributes.addFlashAttribute("semesterDetailShow", true);
        redirectAttributes.addFlashAttribute("semesterDetail", semesterService.persist(semester));
        return "redirect:/semester";
    }

    @GetMapping("/remove/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        semesterService.delete(id);
        model.addAttribute("message", "Successfully deleted");
        return "redirect:/semester";
    }

    @GetMapping("/subject/{id}")
    public String addSubject(@PathVariable Long id, Model model) {
        model.addAttribute("semester", semesterService.findById(id));
        model.addAttribute("subjects", semesterService.findAll());
        model.addAttribute("subjectEmployeeStatuses", SubjectEmployeeStatus.values());
        model.addAttribute("message", "Successfully deleted");
        return "semester/addSemesterSubject";
    }


}

