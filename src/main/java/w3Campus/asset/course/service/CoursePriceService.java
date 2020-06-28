package w3Campus.asset.course.service;

import w3Campus.asset.course.entity.CoursePrice;

import java.util.List;

public interface CoursePriceService {
    List<CoursePrice> findAll();

    CoursePrice findById(Long id);

    CoursePrice persist(CoursePrice coursePrice);

    boolean delete(Long id);

    List<CoursePrice> search(CoursePrice coursePrice);

}
