package lk.w3Academy.w3.asset.userManagement.dao;

import lk.w3Academy.w3.asset.userManagement.entity.Enum.UserSessionLogStatus;
import lk.w3Academy.w3.asset.userManagement.entity.User;
import lk.w3Academy.w3.asset.userManagement.entity.UserSessionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionLogDao extends JpaRepository<UserSessionLog, Integer > {
    UserSessionLog findByUserAndUserSessionLogStatus(User user, UserSessionLogStatus userSessionLogStatus);
}
