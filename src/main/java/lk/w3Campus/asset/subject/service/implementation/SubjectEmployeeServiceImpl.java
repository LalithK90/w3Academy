package lk.w3Campus.asset.subject.service.implementation;


import lk.w3Campus.asset.subject.dao.SubjectEmployeeDao;
import lk.w3Campus.asset.subject.entity.SubjectEmployee;
import lk.w3Campus.asset.subject.service.SubjectEmployeeService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"subjectEmployee"} ) // tells Spring where to store cache for this class
public class SubjectEmployeeServiceImpl implements SubjectEmployeeService {
    private final SubjectEmployeeDao subjectEmployeeDao;

    public SubjectEmployeeServiceImpl(SubjectEmployeeDao subjectEmployeeDao) {
        this.subjectEmployeeDao = subjectEmployeeDao;
    }

    @Cacheable
    public List< SubjectEmployee > findAll() {
        return subjectEmployeeDao.findAll();
    }

    @Cacheable
    @Transactional
    public SubjectEmployee findById(Long id) {
        return subjectEmployeeDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "subjectEmployee", allEntries = true )},
            put = {@CachePut( value = "subjectEmployee", key = "#subjectEmployee.id" )} )
    @Transactional
    public SubjectEmployee persist(SubjectEmployee subjectEmployee) {

        return subjectEmployeeDao.save(subjectEmployee);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted subjectEmployee
        subjectEmployeeDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< SubjectEmployee > search(SubjectEmployee subjectEmployee) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< SubjectEmployee > subjectEmployeeExample = Example.of(subjectEmployee, matcher);
        return subjectEmployeeDao.findAll(subjectEmployeeExample);
    }

}