package lk.w3Campus.asset.userManagement.service.implementation;


import lk.w3Campus.asset.userManagement.dao.ConformationTokenDao;
import lk.w3Campus.asset.userManagement.entity.ConformationToken;
import lk.w3Campus.asset.userManagement.service.ConformationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConformationTokenServiceImpl implements ConformationTokenService {
    private final ConformationTokenDao conformationTokenDao;

    @Autowired
    public ConformationTokenServiceImpl(ConformationTokenDao conformationTokenDao) {
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
