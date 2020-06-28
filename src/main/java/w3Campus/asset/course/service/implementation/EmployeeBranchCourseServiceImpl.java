package w3Campus.asset.course.service.implementation;

import w3Campus.asset.course.dao.EmployeeBranchCourseDao;
import w3Campus.asset.course.entity.EmployeeBranchCourse;
import w3Campus.asset.course.service.EmployeeBranchCourseService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"employeeBranchCourse"}) // tells Spring where to store cache for this class
public class EmployeeBranchCourseServiceImpl implements EmployeeBranchCourseService {
    private final EmployeeBranchCourseDao employeeBranchCourseDao;

    public EmployeeBranchCourseServiceImpl(EmployeeBranchCourseDao employeeBranchCourseDao) {
        this.employeeBranchCourseDao = employeeBranchCourseDao;
    }


    @Cacheable
    public List<EmployeeBranchCourse> findAll() {
        return employeeBranchCourseDao.findAll();
    }

    @Cacheable
    @Transactional
    public EmployeeBranchCourse findById(Long id) {
        return employeeBranchCourseDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "employeeBranchCourse", allEntries = true)},
            put = {@CachePut(value = "employeeBranchCourse", key = "#employeeBranchCourse.id")})
    @Transactional
    public EmployeeBranchCourse persist(EmployeeBranchCourse employeeBranchCourse) {

        return employeeBranchCourseDao.save(employeeBranchCourse);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        //according to this project can not be deleted employeeEmployeeCourse
        employeeBranchCourseDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<EmployeeBranchCourse> search(EmployeeBranchCourse employeeBranchCourse) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<EmployeeBranchCourse> employeeBranchCourseExample = Example.of(employeeBranchCourse, matcher);
        return employeeBranchCourseDao.findAll(employeeBranchCourseExample);
    }


}