package lk.w3Campus.asset.course.controller;

import lk.w3Campus.asset.commonAsset.model.Enum.PriceStatus;
import lk.w3Campus.asset.course.entity.Course;
import lk.w3Campus.asset.course.entity.CoursePrice;
import lk.w3Campus.asset.course.service.BranchCourseService;
import lk.w3Campus.asset.course.service.CoursePriceService;
import lk.w3Campus.asset.course.service.CourseService;
import lk.w3Campus.asset.course.service.EmployeeBranchCourseService;
import lk.w3Campus.asset.employee.service.EmployeeService;
import lk.w3Campus.asset.subject.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/course")
public class CourseController {
    //todo -> 10/06 need to finished
    private final EmployeeService employeeService;
    private final SubjectService subjectService;
    private final BranchCourseService branchCourseService;
    private final CoursePriceService coursePriceService;
    private final CourseService courseService;
    private final EmployeeBranchCourseService employeeBranchCourseService;

    public CourseController(EmployeeService employeeService, SubjectService subjectService, BranchCourseService branchCourseService, CoursePriceService coursePriceService, CourseService courseService, EmployeeBranchCourseService employeeBranchCourseService) {
        this.employeeService = employeeService;
        this.subjectService = subjectService;
        this.branchCourseService = branchCourseService;
        this.coursePriceService = coursePriceService;
        this.courseService = courseService;
        this.employeeBranchCourseService = employeeBranchCourseService;
    }

    @GetMapping
    public String courseAddForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("addStatus", true);
        model.addAttribute("courses", courseService.findAll());
        return "course/course";
    }

    @PostMapping("/add")
    public String persist(@Valid @ModelAttribute Course course, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("course", new Course());
            return "course/course";
        }
        redirectAttributes.addFlashAttribute("course", courseService.persist(course));
        return "redirect:/course/coursePrice";
    }

    @GetMapping("/coursePrice")
    public String coursePrice(Model model) {
        model.addAttribute("coursePrice", new CoursePrice());
        model.addAttribute("priceStatuses", PriceStatus.values());
        model.addAttribute("coursePriceStatus", true);
        model.addAttribute("course", new Course());
        model.addAttribute("addStatus", true);
        return "course/course";
    }

    @PostMapping(value = {"/coursePrice/add", "/coursePrice/update"})
    public String coursePriceSave(@Valid @ModelAttribute CoursePrice coursePrice, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("coursePrice", coursePrice);
            model.addAttribute("priceStatuses", PriceStatus.values());
            model.addAttribute("coursePriceStatus", true);
            return "course/course";
        }
        redirectAttributes.addFlashAttribute("course", coursePriceService.persist(coursePrice));
        //need to redirect to course employee add
        return "/";
    }
}
