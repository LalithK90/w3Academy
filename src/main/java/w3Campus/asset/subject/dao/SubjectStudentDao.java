package w3Campus.asset.subject.dao;

import w3Campus.asset.subject.entity.SubjectStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectStudentDao extends JpaRepository<SubjectStudent, Long> {
}
