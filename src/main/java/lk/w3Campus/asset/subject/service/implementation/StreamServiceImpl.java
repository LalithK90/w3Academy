package lk.w3Campus.asset.subject.service.implementation;


import lk.w3Campus.asset.subject.dao.StreamDao;
import lk.w3Campus.asset.subject.entity.Stream;
import lk.w3Campus.asset.subject.service.StreamService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"stream"} ) // tells Spring where to store cache for this class
public class StreamServiceImpl implements StreamService {
    private final StreamDao streamDao;

    public StreamServiceImpl(StreamDao streamDao) {
        this.streamDao = streamDao;
    }


    @Cacheable
    public List< Stream > findAll() {
        return streamDao.findAll();
    }

    @Cacheable
    @Transactional
    public Stream findById(Long id) {
        return streamDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "stream", allEntries = true )},
            put = {@CachePut( value = "stream", key = "#stream.id" )} )
    @Transactional
    public Stream persist(Stream stream) {

        return streamDao.save(stream);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted stream
        streamDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< Stream > search(Stream stream) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Stream > streamExample = Example.of(stream, matcher);
        return streamDao.findAll(streamExample);
    }


}