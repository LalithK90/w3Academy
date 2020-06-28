package w3Campus.asset.subject.service.implementation;

import w3Campus.asset.subject.dao.SubjectStudentDao;
import w3Campus.asset.subject.entity.SubjectStudent;
import w3Campus.asset.subject.service.SubjectStudentService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"subjectStudent"} ) // tells Spring where to store cache for this class
public class SubjectStudentServiceImpl implements SubjectStudentService {
    private final SubjectStudentDao subjectStudentDao;

    public SubjectStudentServiceImpl(SubjectStudentDao subjectStudentDao) {
        this.subjectStudentDao = subjectStudentDao;
    }

    @Cacheable
    public List< SubjectStudent > findAll() {
        return subjectStudentDao.findAll();
    }

    @Cacheable
    @Transactional
    public SubjectStudent findById(Long id) {
        return subjectStudentDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "subjectStudent", allEntries = true )},
            put = {@CachePut( value = "subjectStudent", key = "#subjectStudent.id" )} )
    @Transactional
    public SubjectStudent persist(SubjectStudent subjectStudent) {

        return subjectStudentDao.save(subjectStudent);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted subjectStudent
        subjectStudentDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< SubjectStudent > search(SubjectStudent subjectStudent) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< SubjectStudent > subjectStudentExample = Example.of(subjectStudent, matcher);
        return subjectStudentDao.findAll(subjectStudentExample);
    }

}