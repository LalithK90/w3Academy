package lk.w3Campus.asset.course.controller;

import lk.w3Campus.asset.course.service.*;
import lk.w3Campus.asset.employee.service.EmployeeService;
import lk.w3Campus.asset.subject.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final EmployeeService employeeService;
    private final SubjectService subjectService;
    private final BranchCourseService branchCourseService;
    private final CourseDetailService courseDetailService;
    private final CoursePriceService coursePriceService;
    private final CourseService courseService;
    private final EmployeeCourseService employeeCourseService;

    public CourseController(EmployeeService employeeService, SubjectService subjectService, BranchCourseService branchCourseService, CourseDetailService courseDetailService, CoursePriceService coursePriceService, CourseService courseService, EmployeeCourseService employeeCourseService) {
        this.employeeService = employeeService;
        this.subjectService = subjectService;
        this.branchCourseService = branchCourseService;
        this.courseDetailService = courseDetailService;
        this.coursePriceService = coursePriceService;
        this.courseService = courseService;
        this.employeeCourseService = employeeCourseService;
    }

    @GetMapping
    private void findAll() {

    }

}
