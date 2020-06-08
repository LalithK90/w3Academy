package lk.w3Academy.asset.branch.dao;

import lk.w3Academy.asset.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchDao extends JpaRepository<Branch, Long> {
}
