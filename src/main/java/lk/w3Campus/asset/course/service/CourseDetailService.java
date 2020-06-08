package lk.w3Campus.asset.course.service;

import lk.w3Campus.asset.course.entity.CourseDetail;

import java.util.List;

public interface CourseDetailService {
    List<CourseDetail> findAll();

    CourseDetail findById(Long id);

    CourseDetail persist(CourseDetail courseDetail);

    boolean delete(Long id);

    List<CourseDetail> search(CourseDetail courseDetail);
}
