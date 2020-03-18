package lk.w3Academy.w3.asset.message.dao;

import lk.w3Academy.w3.asset.message.entity.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailMessageDao extends JpaRepository<EmailMessage, Integer > {
}
