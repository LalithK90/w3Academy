package w3Campus.asset.subject.dao;


import w3Campus.asset.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectDao extends JpaRepository<Subject, Long > {


   }
