package com.iuh.edu.fit.backend.service.email;

import com.iuh.edu.fit.backend.dto.DataMailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class MailService implements IMailService{

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Override
    public void sendInvitationEmail(DataMailDTO dataMailDTO, String template) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        Context context = new Context();
        context.setVariable("dataMailDTO", dataMailDTO.getProps());
        String html = templateEngine.process(template, context);
        helper.setTo(dataMailDTO.getTo());
        helper.setSubject(dataMailDTO.getSubject());
        helper.setText(html, true);

        mailSender.send(mimeMessage);
    }
}
