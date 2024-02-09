package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost("smtp.gmail.com");
        javaMailSenderImpl.setPort(587);
        javaMailSenderImpl.setUsername("p.muhamad@axelor.com");
        javaMailSenderImpl.setPassword("Par@axelor2021");
        Properties javaMailProperties = javaMailSenderImpl.getJavaMailProperties();
        javaMailProperties.put("mail.smtp.starttls.enable",true);
        javaMailProperties.put("mail.debug",true);
        return javaMailSenderImpl;
    }
}
