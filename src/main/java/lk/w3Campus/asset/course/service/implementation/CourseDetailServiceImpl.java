package lk.w3Campus.asset.course.service.implementation;


import lk.w3Campus.asset.course.dao.CourseDetailDao;
import lk.w3Campus.asset.course.entity.CourseDetail;
import lk.w3Campus.asset.course.service.CourseDetailService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"courseDetail"} ) // tells Spring where to store cache for this class
public class CourseDetailServiceImpl implements CourseDetailService {
    private final CourseDetailDao courseDetailDao;

    public CourseDetailServiceImpl(CourseDetailDao courseDetailDao) {
        this.courseDetailDao = courseDetailDao;
    }


    @Cacheable
    public List<CourseDetail> findAll() {
        return courseDetailDao.findAll();
    }

    @Cacheable
    @Transactional
    public CourseDetail findById(Long id) {
        return courseDetailDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "courseDetail", allEntries = true )},
            put = {@CachePut( value = "courseDetail", key = "#courseDetail.id" )} )
    @Transactional
    public CourseDetail persist(CourseDetail courseDetail) {

        return courseDetailDao.save(courseDetail);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted courseDetail
        courseDetailDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< CourseDetail > search(CourseDetail courseDetail) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< CourseDetail > courseDetailExample = Example.of(courseDetail, matcher);
        return courseDetailDao.findAll(courseDetailExample);
    }


}