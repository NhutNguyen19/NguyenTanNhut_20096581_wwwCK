package com.iuh.edu.fit.backend.service.email;

import com.iuh.edu.fit.backend.dto.DataMailDTO;
import jakarta.mail.MessagingException;

public interface IMailService {
    void sendInvitationEmail(DataMailDTO dataMailDTO, String template) throws MessagingException;
}
