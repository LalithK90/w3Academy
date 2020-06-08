package lk.w3Campus.asset.course.service;

import lk.w3Campus.asset.course.entity.BranchCourse;
import lk.w3Campus.asset.course.entity.Course;

import java.util.List;

public interface BranchCourseService {
    List<BranchCourse> findAll();

    BranchCourse findById(Long id);

    BranchCourse persist(BranchCourse branchCourse);

    boolean delete(Long id);

    List<BranchCourse> search(BranchCourse branchCourse);

}
