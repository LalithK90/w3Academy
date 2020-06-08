package lk.w3Campus.asset.subject.dao;


import lk.w3Campus.asset.subject.entity.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamDao extends JpaRepository<Stream, Long> {

}
