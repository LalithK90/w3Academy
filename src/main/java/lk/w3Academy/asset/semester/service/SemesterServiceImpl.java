package lk.w3Academy.asset.semester.service;



import lk.w3Academy.asset.semester.dao.SemesterDao;
import lk.w3Academy.asset.semester.entity.Semester;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"semester"} ) // tells Spring where to store cache for this class
public class SemesterServiceImpl implements SemesterService {
    private final SemesterDao semesterDao;

    public SemesterServiceImpl(SemesterDao semesterDao) {
        this.semesterDao = semesterDao;
    }


    @Cacheable
    public List< Semester > findAll() {
        return semesterDao.findAll();
    }

    @Cacheable
    @Transactional
    public Semester findById(Long id) {
        return semesterDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "semester", allEntries = true )},
            put = {@CachePut( value = "semester", key = "#semester.id" )} )
    @Transactional
    public Semester persist(Semester semester) {

        return semesterDao.save(semester);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted semester
        semesterDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< Semester > search(Semester semester) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Semester > semesterExample = Example.of(semester, matcher);
        return semesterDao.findAll(semesterExample);
    }


}