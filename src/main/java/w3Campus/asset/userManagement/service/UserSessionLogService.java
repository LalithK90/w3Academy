package w3Campus.asset.userManagement.service;

import w3Campus.asset.userManagement.entity.Enum.UserSessionLogStatus;
import w3Campus.asset.userManagement.entity.User;
import w3Campus.asset.userManagement.entity.UserSessionLog;

import java.util.List;

public interface UserSessionLogService {
    List<UserSessionLog> findAll();

    UserSessionLog findById(Long id);

    UserSessionLog persist(UserSessionLog userSessionLog);

    boolean delete(Long id);

    List<UserSessionLog> search(UserSessionLog userSessionLog);
    UserSessionLog findByUserAndUserSessionLogStatus(User user, UserSessionLogStatus userSessionLogStatus);
}
