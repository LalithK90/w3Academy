package w3Campus.asset.subject.service;

import w3Campus.asset.subject.entity.SubjectStudent;

import java.util.List;

public interface SubjectStudentService {
    List<SubjectStudent> findAll();

    SubjectStudent findById(Long id);

    SubjectStudent persist(SubjectStudent subjectStudent);

    boolean delete(Long id);

    List<SubjectStudent> search(SubjectStudent subjectStudent);
}
