package w3Campus.asset.subject.service.implementation;


import w3Campus.asset.subject.dao.SubjectSemesterDao;
import w3Campus.asset.subject.entity.SubjectSemester;
import w3Campus.asset.subject.service.SubjectSemesterService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"subjectSemester"} ) // tells Spring where to store cache for this class
public class SubjectSemesterServiceImpl implements SubjectSemesterService {
    private final SubjectSemesterDao subjectSemesterDao;

    public SubjectSemesterServiceImpl(SubjectSemesterDao subjectSemesterDao) {
        this.subjectSemesterDao = subjectSemesterDao;
    }


    @Cacheable
    public List< SubjectSemester > findAll() {
        return subjectSemesterDao.findAll();
    }

    @Cacheable
    @Transactional
    public SubjectSemester findById(Long id) {
        return subjectSemesterDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "subjectSemester", allEntries = true )},
            put = {@CachePut( value = "subjectSemester", key = "#subjectSemester.id" )} )
    @Transactional
    public SubjectSemester persist(SubjectSemester subjectSemester) {

        return subjectSemesterDao.save(subjectSemester);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted subjectSemester
        subjectSemesterDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< SubjectSemester > search(SubjectSemester subjectSemester) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< SubjectSemester > subjectSemesterExample = Example.of(subjectSemester, matcher);
        return subjectSemesterDao.findAll(subjectSemesterExample);
    }


}