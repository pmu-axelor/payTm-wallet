package org.example.service;

import org.example.dto.SendMailNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMailNotificationToUsers(SendMailNotification sendMailNotification) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("p.muhamad@axelor.com");
        simpleMailMessage.setTo(sendMailNotification.getReceviersMailId());
        simpleMailMessage.setSubject(sendMailNotification.getMailSubject());
        simpleMailMessage.setText(sendMailNotification.getMailBody());
        javaMailSender.send(simpleMailMessage);
    }
}
