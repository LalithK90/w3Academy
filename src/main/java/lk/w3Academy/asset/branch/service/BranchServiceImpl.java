package lk.w3Academy.asset.branch.service;



import lk.w3Academy.asset.branch.dao.BranchDao;
import lk.w3Academy.asset.branch.entity.Branch;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"branch"} ) // tells Spring where to store cache for this class
public class BranchServiceImpl implements BranchService {
    private final BranchDao branchDao;

    public BranchServiceImpl(BranchDao branchDao) {
        this.branchDao = branchDao;
    }


    @Cacheable
    public List< Branch > findAll() {
        return branchDao.findAll();
    }

    @Cacheable
    @Transactional
    public Branch findById(Long id) {
        return branchDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "branch", allEntries = true )},
            put = {@CachePut( value = "branch", key = "#branch.id" )} )
    @Transactional
    public Branch persist(Branch branch) {

        return branchDao.save(branch);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted branch
        branchDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< Branch > search(Branch branch) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Branch > branchExample = Example.of(branch, matcher);
        return branchDao.findAll(branchExample);
    }


}