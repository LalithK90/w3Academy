package lk.w3Campus.asset.student.service;

import lk.w3Campus.asset.commonAsset.model.FileInfo;
import lk.w3Campus.asset.student.entity.Student;
import lk.w3Campus.asset.student.entity.StudentFiles;

import java.util.List;

public interface StudentFilesService {
    StudentFiles findByName(String filename);
    void persist(StudentFiles storedFile);
    List< StudentFiles > search(StudentFiles studentFiles);
    StudentFiles findById(Long id);
    StudentFiles findByNewID(String filename);
    List<FileInfo> studentFileDownloadLinks(Student student);
}
