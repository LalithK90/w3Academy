package w3Campus.asset.semester.dao;


import w3Campus.asset.semester.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterDao extends JpaRepository<Semester, Long > {


   }
