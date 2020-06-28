package w3Campus.asset.course.service;

import w3Campus.asset.course.entity.BranchCourse;

import java.util.List;

public interface BranchCourseService {
    List<BranchCourse> findAll();

    BranchCourse findById(Long id);

    BranchCourse persist(BranchCourse branchCourse);

    boolean delete(Long id);

    List<BranchCourse> search(BranchCourse branchCourse);

}
