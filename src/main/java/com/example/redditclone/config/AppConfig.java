package com.example.redditclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class AppConfig {
    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("nhtrungddab@gmail.com");
        javaMailSender.setPassword("son26122004");
        javaMailSender.setPort(567);

        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");

        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

}
