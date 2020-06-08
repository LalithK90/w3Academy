package lk.w3Academy.asset.message.service;

import lk.w3Academy.asset.message.entity.EmailMessage;

import java.util.List;

public interface EmailMessageService {
    List<EmailMessage> findAll();

    EmailMessage findById(Long id);

    EmailMessage persist(EmailMessage emailMessage);

    boolean delete(Long id);

    List<EmailMessage> search(EmailMessage emailMessage);

}
