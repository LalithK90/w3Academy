package lk.w3Campus.asset.userManagement.service;

import lk.w3Campus.asset.userManagement.entity.ConformationToken;

public interface ConformationTokenService {
    ConformationToken persist(ConformationToken conformationToken);

    ConformationToken findByToken(String token);

    ConformationToken findByEmail(String email);

    void deleteByConformationToken(ConformationToken conformationToken);
}
