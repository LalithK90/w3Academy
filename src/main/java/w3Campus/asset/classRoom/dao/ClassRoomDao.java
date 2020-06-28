package w3Campus.asset.classRoom.dao;

import w3Campus.asset.classRoom.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomDao extends JpaRepository<ClassRoom, Long> {
}
