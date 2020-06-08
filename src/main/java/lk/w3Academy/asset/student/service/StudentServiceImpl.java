package lk.w3Academy.asset.student.service;

import lk.w3Academy.asset.student.dao.StudentDao;
import lk.w3Academy.asset.student.entity.Student;
import lk.w3Academy.asset.userManagement.dao.UserDao;
import lk.w3Academy.asset.userManagement.entity.Enum.UType;
import lk.w3Academy.asset.userManagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "student")
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final UserDao userDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao, UserDao userDao) {
        this.studentDao = studentDao;
        this.userDao = userDao;
    }

    @Cacheable
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Cacheable
    public Student findById(Long id) {
        return studentDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "student", allEntries = true)},
            put = {@CachePut(value = "student", key = "#student.id")})
    @Transactional
    public Student persist(Student student) {
        if (student.getId() == null) {
            Student student1 = studentDao.save(student);
            User user = userDao.findByUsername(student.getEmail());
            if (user != null && user.getUType().equals(UType.NONSPECIFIC)) {
                user.setStudent(student1);
                user.setUType(UType.STUDENT);
                userDao.save(user);
            }
            return student1;
        }
        return studentDao.save(student);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        studentDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<Student> search(Student student) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Student> studentExample = Example.of(student, matcher);
        return studentDao.findAll(studentExample);
    }

    public boolean isStudentPresent(Student student) {
        return studentDao.equals(student);
    }


    public Student lastStudent() {
        return studentDao.findFirstByOrderByIdDesc();
    }

    @Cacheable
    public Student findByNic(String nic) {
        return studentDao.findByNic(nic);
    }
}
