package lk.w3Campus.asset.student.dao;

import lk.w3Campus.asset.student.entity.Student;
import lk.w3Campus.asset.student.entity.StudentFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentFilesDao extends JpaRepository<StudentFiles, Long > {
    List< StudentFiles > findByStudentOrderByIdDesc(Student student);

    StudentFiles findByName(String filename);

    StudentFiles findByNewName(String filename);

    StudentFiles findByNewId(String filename);
}
