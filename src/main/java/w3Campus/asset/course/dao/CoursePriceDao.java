package w3Campus.asset.course.dao;

import w3Campus.asset.course.entity.CoursePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePriceDao extends JpaRepository<CoursePrice, Long> {
}
