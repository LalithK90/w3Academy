package lk.w3Campus.asset.course.service.implementation;

import lk.w3Campus.asset.course.dao.EmployeeCourseDao;
import lk.w3Campus.asset.course.entity.EmployeeCourse;
import lk.w3Campus.asset.course.service.EmployeeCourseService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"employeeCourse"}) // tells Spring where to store cache for this class
public class EmployeeCourseServiceImpl implements EmployeeCourseService {
    private final EmployeeCourseDao employeeCourseDao;

    public EmployeeCourseServiceImpl(EmployeeCourseDao employeeCourseDao) {
        this.employeeCourseDao = employeeCourseDao;
    }


    @Cacheable
    public List<EmployeeCourse> findAll() {
        return employeeCourseDao.findAll();
    }

    @Cacheable
    @Transactional
    public EmployeeCourse findById(Long id) {
        return employeeCourseDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "employeeEmployeeCourse", allEntries = true)},
            put = {@CachePut(value = "employeeEmployeeCourse", key = "#employeeEmployeeCourse.id")})
    @Transactional
    public EmployeeCourse persist(EmployeeCourse employeeEmployeeCourse) {

        return employeeCourseDao.save(employeeEmployeeCourse);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        //according to this project can not be deleted employeeEmployeeCourse
        employeeCourseDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<EmployeeCourse> search(EmployeeCourse employeeEmployeeCourse) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<EmployeeCourse> employeeEmployeeCourseExample = Example.of(employeeEmployeeCourse, matcher);
        return employeeCourseDao.findAll(employeeEmployeeCourseExample);
    }


}