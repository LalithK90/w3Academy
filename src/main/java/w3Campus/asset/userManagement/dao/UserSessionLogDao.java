package w3Campus.asset.userManagement.dao;

import w3Campus.asset.userManagement.entity.Enum.UserSessionLogStatus;
import w3Campus.asset.userManagement.entity.User;
import w3Campus.asset.userManagement.entity.UserSessionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionLogDao extends JpaRepository<UserSessionLog, Long > {
    UserSessionLog findByUserAndUserSessionLogStatus(User user, UserSessionLogStatus userSessionLogStatus);
}
