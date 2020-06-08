package lk.w3Academy.asset.semester.service;

import lk.w3Academy.asset.semester.entity.Semester;

import java.util.List;

public interface SemesterService {
    List<Semester> findAll();

    Semester findById(Long id);

    Semester persist(Semester semester);

    boolean delete(Long id);

    List<Semester> search(Semester semester);
}
