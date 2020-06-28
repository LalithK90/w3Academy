package w3Campus.asset.course.service;

import w3Campus.asset.course.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();

    Course findById(Long id);

    Course persist(Course course);

    boolean delete(Long id);

    List<Course> search(Course course);

}
