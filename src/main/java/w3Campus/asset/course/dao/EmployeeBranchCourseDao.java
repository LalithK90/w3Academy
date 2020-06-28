package w3Campus.asset.course.dao;


import w3Campus.asset.course.entity.EmployeeBranchCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeBranchCourseDao extends JpaRepository<EmployeeBranchCourse, Long> {
}
