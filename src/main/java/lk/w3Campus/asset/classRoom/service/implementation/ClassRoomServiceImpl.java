package lk.w3Campus.asset.classRoom.service.implementation;


import lk.w3Campus.asset.classRoom.dao.ClassRoomDao;
import lk.w3Campus.asset.classRoom.entity.ClassRoom;
import lk.w3Campus.asset.classRoom.service.ClassRoomService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"classRoom"} ) // tells Spring where to store cache for this class
public class ClassRoomServiceImpl implements ClassRoomService {
    private final ClassRoomDao classRoomDao;

    public ClassRoomServiceImpl(ClassRoomDao classRoomDao) {
        this.classRoomDao = classRoomDao;
    }


    @Cacheable
    public List< ClassRoom > findAll() {
        return classRoomDao.findAll();
    }

    @Cacheable
    @Transactional
    public ClassRoom findById(Long id) {
        return classRoomDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "classRoom", allEntries = true )},
            put = {@CachePut( value = "classRoom", key = "#classRoom.id" )} )
    @Transactional
    public ClassRoom persist(ClassRoom classRoom) {

        return classRoomDao.save(classRoom);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted classRoom
        classRoomDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< ClassRoom > search(ClassRoom classRoom) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< ClassRoom > classRoomExample = Example.of(classRoom, matcher);
        return classRoomDao.findAll(classRoomExample);
    }


}