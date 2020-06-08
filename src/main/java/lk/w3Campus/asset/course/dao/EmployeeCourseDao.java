package lk.w3Campus.asset.course.dao;

import lk.w3Campus.asset.course.entity.EmployeeCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCourseDao extends JpaRepository<EmployeeCourse, Long> {
}
