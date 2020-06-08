package lk.w3Academy.asset.course.service;

import lk.w3Academy.asset.course.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();

    Course findById(Long id);

    Course persist(Course course);

    boolean delete(Long id);

    List<Course> search(Course course);

}
