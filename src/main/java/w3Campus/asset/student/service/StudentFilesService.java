package w3Campus.asset.student.service;

import org.springframework.transaction.annotation.Transactional;
import w3Campus.asset.commonAsset.model.FileInfo;
import w3Campus.asset.student.entity.Student;
import w3Campus.asset.student.entity.StudentFiles;

import java.util.List;

public interface StudentFilesService {
    StudentFiles findByName(String filename);

    void persist(StudentFiles storedFile);

    List<StudentFiles> search(StudentFiles studentFiles);

    StudentFiles findById(Long id);

    StudentFiles findByNewID(String filename);

    FileInfo studentFileDownloadLink(Student student);

    StudentFiles findByStudent(Student dbStudent);

    @Transactional
    void deleteByStudent(Student student);
}
