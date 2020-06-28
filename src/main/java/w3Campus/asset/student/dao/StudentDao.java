package w3Campus.asset.student.dao;

import w3Campus.asset.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
    Student findFirstByOrderByIdDesc();

    Student findByNic(String nic);
}
