package lk.w3Academy.asset.course.dao;


import lk.w3Academy.asset.course.entity.Course;
import lk.w3Academy.asset.semester.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course, Long > {


   }
