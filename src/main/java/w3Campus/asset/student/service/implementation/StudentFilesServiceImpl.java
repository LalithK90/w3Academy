package w3Campus.asset.student.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import w3Campus.asset.commonAsset.model.FileInfo;
import w3Campus.asset.student.controller.StudentController;
import w3Campus.asset.student.dao.StudentFilesDao;
import w3Campus.asset.student.entity.Student;
import w3Campus.asset.student.entity.StudentFiles;
import w3Campus.asset.student.service.StudentFilesService;

import java.util.List;

@Service
@CacheConfig(cacheNames = "studentFiles")
public class StudentFilesServiceImpl implements StudentFilesService {
    private final StudentFilesDao studentFilesDao;

    @Autowired
    public StudentFilesServiceImpl(StudentFilesDao studentFilesDao) {
        this.studentFilesDao = studentFilesDao;
    }

    public StudentFiles findByName(String filename) {
        return studentFilesDao.findByName(filename);
    }

    public void persist(StudentFiles storedFile) {
        studentFilesDao.save(storedFile);
    }


    public List<StudentFiles> search(StudentFiles studentFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<StudentFiles> studentFilesExample = Example.of(studentFiles, matcher);
        return studentFilesDao.findAll(studentFilesExample);
    }

    public StudentFiles findById(Long id) {
        return studentFilesDao.getOne(id);
    }

    public StudentFiles findByNewID(String filename) {
        return studentFilesDao.findByNewId(filename);
    }

    @Cacheable
    public FileInfo studentFileDownloadLink(Student student) {
        StudentFiles studentFiles = studentFilesDao.findByStudent(student);
        if (studentFiles != null) {
            return new FileInfo(studentFiles.getName(), studentFiles.getCreatedAt(), MvcUriComponentsBuilder
                    .fromMethodName(StudentController.class, "downloadFile", studentFiles.getNewId())
                    .build()
                    .toString());
        } else {
            return null;
        }
    }

    @Override
    public StudentFiles findByStudent(Student student) {
        return studentFilesDao.findByStudent(student);
    }

    @Override
    public void deleteByStudent(Student student) {
        studentFilesDao.deleteByStudent(student);
    }
}
