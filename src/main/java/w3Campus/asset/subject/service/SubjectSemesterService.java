package w3Campus.asset.subject.service;

import w3Campus.asset.subject.entity.SubjectSemester;

import java.util.List;

public interface SubjectSemesterService {
    List<SubjectSemester> findAll();

    SubjectSemester findById(Long id);

    SubjectSemester persist(SubjectSemester subjectSemester);

    boolean delete(Long id);

    List<SubjectSemester> search(SubjectSemester subjectSemester);
}
