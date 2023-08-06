package com.example.redditclone.service;

import com.example.redditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    @Autowired

    private JavaMailSender javaMailSender;

    public void send(NotificationEmail notificationEmail){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(notificationEmail.getRecipient());
        simpleMailMessage.setSubject(notificationEmail.getSubject());
        simpleMailMessage.setText(notificationEmail.getBody());
//        MimeMessagePreparator  mimeMessagePreparator = mimeMessage -> {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//            mimeMessageHelper.setSubject(notificationEmail.getSubject());
//            mimeMessageHelper.setFrom("wik83886@nezid.com");
//            mimeMessageHelper.setTo(notificationEmail.getRecipient());
//            mimeMessageHelper.setText(notificationEmail.getBody());
//        };

        try{
            javaMailSender.send(simpleMailMessage);
        }catch (MailException e){

        }

    }

}
