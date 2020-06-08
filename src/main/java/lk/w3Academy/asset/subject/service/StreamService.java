package lk.w3Academy.asset.subject.service;

import lk.w3Academy.asset.subject.entity.Stream;

import java.util.List;

public interface StreamService {
    List<Stream> findAll();

    Stream findById(Long id);

    Stream persist(Stream stream);

    boolean delete(Long id);

    List<Stream> search(Stream stream);
}
