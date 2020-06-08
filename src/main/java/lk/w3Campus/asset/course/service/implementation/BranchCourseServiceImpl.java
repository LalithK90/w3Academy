package lk.w3Campus.asset.course.service.implementation;


import lk.w3Campus.asset.course.dao.BranchCourseDao;
import lk.w3Campus.asset.course.entity.BranchCourse;
import lk.w3Campus.asset.course.service.BranchCourseService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"branchCourse"}) // tells Spring where to store cache for this class
public class BranchCourseServiceImpl implements BranchCourseService {
    private final BranchCourseDao branchCourseDao;

    public BranchCourseServiceImpl(BranchCourseDao branchCourseDao) {
        this.branchCourseDao = branchCourseDao;
    }


    @Cacheable
    public List<BranchCourse> findAll() {
        return branchCourseDao.findAll();
    }

    @Cacheable
    @Transactional
    public BranchCourse findById(Long id) {
        return branchCourseDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "branchCourse", allEntries = true)},
            put = {@CachePut(value = "branchCourse", key = "#branchCourse.id")})
    @Transactional
    public BranchCourse persist(BranchCourse branchCourse) {

        return branchCourseDao.save(branchCourse);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        //according to this project can not be deleted branchCourse
        branchCourseDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<BranchCourse> search(BranchCourse branchCourse) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<BranchCourse> branchCourseExample = Example.of(branchCourse, matcher);
        return branchCourseDao.findAll(branchCourseExample);
    }


}