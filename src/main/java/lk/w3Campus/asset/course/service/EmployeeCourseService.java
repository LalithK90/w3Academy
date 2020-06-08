package lk.w3Campus.asset.course.service;

import lk.w3Campus.asset.course.entity.EmployeeCourse;

import java.util.List;

public interface EmployeeCourseService {
    List<EmployeeCourse> findAll();

    EmployeeCourse findById(Long id);

    EmployeeCourse persist(EmployeeCourse employeeCourse);

    boolean delete(Long id);

    List<EmployeeCourse> search(EmployeeCourse employeeCourse);

}
