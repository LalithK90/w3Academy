package lk.w3Campus.asset.subject.service;


import lk.w3Campus.asset.subject.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();

    Subject findById(Long id);

    Subject persist(Subject subject);

    boolean delete(Long id);

    List<Subject> search(Subject subject);
}
