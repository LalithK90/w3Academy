package w3Campus.asset.student.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import w3Campus.asset.student.entity.Student;
import w3Campus.asset.student.entity.StudentFiles;

import java.util.List;

@Repository
public interface StudentFilesDao extends JpaRepository<StudentFiles, Long> {
    List<StudentFiles> findByStudentOrderByIdDesc(Student student);

    StudentFiles findByName(String filename);

    StudentFiles findByNewName(String filename);

    StudentFiles findByNewId(String filename);

    StudentFiles findByStudent(Student student);

    void deleteByStudent(Student student);
}
