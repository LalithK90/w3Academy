package lk.w3Academy.asset.semester.dao;


import lk.w3Academy.asset.semester.entity.Semester;
import lk.w3Academy.asset.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterDao extends JpaRepository<Semester, Long > {


   }
