package lk.w3Academy.asset.userManagement.service;

import lk.w3Academy.asset.userManagement.entity.Enum.UserSessionLogStatus;
import lk.w3Academy.asset.userManagement.entity.User;
import lk.w3Academy.asset.userManagement.entity.UserSessionLog;

import java.util.List;

public interface UserSessionLogService {
    List<UserSessionLog> findAll();

    UserSessionLog findById(Long id);

    UserSessionLog persist(UserSessionLog userSessionLog);

    boolean delete(Long id);

    List<UserSessionLog> search(UserSessionLog userSessionLog);
    UserSessionLog findByUserAndUserSessionLogStatus(User user, UserSessionLogStatus userSessionLogStatus);
}
