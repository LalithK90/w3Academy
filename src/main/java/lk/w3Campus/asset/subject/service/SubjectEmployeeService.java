package lk.w3Campus.asset.subject.service;

import lk.w3Campus.asset.subject.entity.SubjectEmployee;

import java.util.List;

public interface SubjectEmployeeService {
    List<SubjectEmployee> findAll();

    SubjectEmployee findById(Long id);

    SubjectEmployee persist(SubjectEmployee subjectEmployee);

    boolean delete(Long id);

    List<SubjectEmployee> search(SubjectEmployee subjectEmployee);
}
