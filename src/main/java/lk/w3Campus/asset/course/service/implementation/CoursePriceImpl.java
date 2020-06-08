package lk.w3Campus.asset.course.service.implementation;


import lk.w3Campus.asset.course.dao.CoursePriceDao;
import lk.w3Campus.asset.course.entity.CoursePrice;
import lk.w3Campus.asset.course.service.CoursePriceService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"coursePrice"}) // tells Spring where to store cache for this class
public class CoursePriceImpl implements CoursePriceService {
    private final CoursePriceDao coursePriceDao;

    public CoursePriceImpl(CoursePriceDao coursePriceDao) {
        this.coursePriceDao = coursePriceDao;
    }


    @Cacheable
    public List<CoursePrice> findAll() {
        return coursePriceDao.findAll();
    }

    @Cacheable
    @Transactional
    public CoursePrice findById(Long id) {
        return coursePriceDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "course", allEntries = true)},
            put = {@CachePut(value = "course", key = "#course.id")})
    @Transactional
    public CoursePrice persist(CoursePrice course) {
        return coursePriceDao.save(course);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        //according to this project can not be deleted course
        coursePriceDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<CoursePrice> search(CoursePrice course) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<CoursePrice> courseExample = Example.of(course, matcher);
        return coursePriceDao.findAll(courseExample);
    }


}