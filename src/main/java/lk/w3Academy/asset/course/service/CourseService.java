package lk.w3Academy.asset.course.service;


import lk.w3Academy.asset.course.dao.CourseDao;
import lk.w3Academy.asset.course.entity.Course;
import lk.w3Academy.util.interfaces.AbstractService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"course"} ) // tells Spring where to store cache for this class
public class CourseService implements AbstractService<Course, Long > {
    private final CourseDao courseDao;

    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }


    @Cacheable
    public List< Course > findAll() {
        return courseDao.findAll();
    }

    @Cacheable
    @Transactional
    public Course findById(Long id) {
        return courseDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "course", allEntries = true )},
            put = {@CachePut( value = "course", key = "#course.id" )} )
    @Transactional
    public Course persist(Course course) {

        return courseDao.save(course);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted course
        courseDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< Course > search(Course course) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Course > courseExample = Example.of(course, matcher);
        return courseDao.findAll(courseExample);
    }


}