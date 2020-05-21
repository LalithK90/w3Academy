package lk.w3Academy.asset.userManagement.dao;

import lk.w3Academy.asset.userManagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long > {
    Role findByRoleName(String roleName);
}
