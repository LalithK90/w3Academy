package lk.w3Campus.asset.course.service;


import lk.w3Campus.asset.course.entity.EmployeeBranchCourse;

import java.util.List;

public interface EmployeeBranchCourseService {
    List<EmployeeBranchCourse> findAll();

    EmployeeBranchCourse findById(Long id);

    EmployeeBranchCourse persist(EmployeeBranchCourse employeeBranchCourse);

    boolean delete(Long id);

    List<EmployeeBranchCourse> search(EmployeeBranchCourse employeeBranchCourse);

}
