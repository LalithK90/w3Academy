package lk.w3Academy.asset.subject.service;


import lk.w3Academy.asset.subject.dao.SubjectDao;
import lk.w3Academy.asset.subject.entity.Subject;
import lk.w3Academy.util.interfaces.AbstractService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"subject"} ) // tells Spring where to store cache for this class
public class SubjectService implements AbstractService<Subject, Long > {
    private final SubjectDao subjectDao;

    public SubjectService(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }


    @Cacheable
    public List< Subject > findAll() {
        return subjectDao.findAll();
    }

    @Cacheable
    @Transactional
    public Subject findById(Long id) {
        return subjectDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "subject", allEntries = true )},
            put = {@CachePut( value = "subject", key = "#subject.id" )} )
    @Transactional
    public Subject persist(Subject subject) {

        return subjectDao.save(subject);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted subject
        subjectDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< Subject > search(Subject subject) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Subject > subjectExample = Example.of(subject, matcher);
        return subjectDao.findAll(subjectExample);
    }


}