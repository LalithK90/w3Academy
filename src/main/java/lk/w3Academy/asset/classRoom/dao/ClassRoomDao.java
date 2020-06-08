package lk.w3Academy.asset.classRoom.dao;

import lk.w3Academy.asset.classRoom.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomDao extends JpaRepository<ClassRoom, Long> {
}
