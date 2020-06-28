package w3Campus.asset.classRoom.service;

import w3Campus.asset.classRoom.entity.ClassRoom;

import java.util.List;

public interface ClassRoomService {
    List<ClassRoom> findAll();

    ClassRoom findById(Long id);

    ClassRoom persist(ClassRoom classRoom);

    boolean delete(Long id);

    List<ClassRoom> search(ClassRoom classRoom);
}
