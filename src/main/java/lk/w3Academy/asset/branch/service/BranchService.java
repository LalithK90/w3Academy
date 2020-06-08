package lk.w3Academy.asset.branch.service;

import lk.w3Academy.asset.branch.entity.Branch;

import java.util.List;

public interface BranchService {
    List<Branch> findAll();

    Branch findById(Long id);

    Branch persist(Branch branch);

    boolean delete(Long id);

    List<Branch> search(Branch branch);
}
