package lk.w3Academy.asset.userManagement.service;


import lk.w3Academy.asset.userManagement.dao.ConformationTokenDao;
import lk.w3Academy.asset.userManagement.entity.ConformationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConformationTokenService {
    private final ConformationTokenDao conformationTokenDao;

    @Autowired
    public ConformationTokenService(ConformationTokenDao conformationTokenDao) {
        this.conformationTokenDao = conformationTokenDao;
    }

    public ConformationToken persist(ConformationToken conformationToken) {
        return conformationTokenDao.save(conformationToken);
    }

    public ConformationToken findByToken(String token) {
        return conformationTokenDao.findByToken(token);
    }

    public ConformationToken findByEmail(String email) {
        return conformationTokenDao.findByEmail(email);
    }

    public void deleteByConformationToken(ConformationToken conformationToken) {
    conformationTokenDao.delete(conformationToken);
    }
}
