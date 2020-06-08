package lk.w3Academy.asset.message.service;

import lk.w3Academy.asset.message.dao.EmailMessageDao;
import lk.w3Academy.asset.message.entity.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig( cacheNames = "emailMessage" )
public class EmailMessageServiceImpl implements EmailMessageService {
    private final EmailMessageDao emailMessageDao;

    @Autowired
    public EmailMessageServiceImpl(EmailMessageDao emailMessageDao) {
        this.emailMessageDao = emailMessageDao;
    }


    @Cacheable
    public List< EmailMessage > findAll() {
        return emailMessageDao.findAll();
    }


    @Cacheable
    public EmailMessage findById(Long id) {
        return emailMessageDao.getOne(id);
    }


    @Caching( evict = {@CacheEvict( value = "emailMessage", allEntries = true )},
            put = {@CachePut( value = "emailMessage", key = "#emailMessage.id" )} )
    public EmailMessage persist(EmailMessage emailMessage) {
        return emailMessageDao.save(emailMessage);
    }


    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        return false;
    }

   @Cacheable
    public List< EmailMessage > search(EmailMessage emailMessage) {
        return null;
    }
}
