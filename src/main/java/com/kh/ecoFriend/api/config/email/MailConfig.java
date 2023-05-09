package com.kh.ecoFriend.api.config.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

  private Properties getMailProperties() {
    Properties properties = new Properties();
    properties.setProperty("mail.transport.protocol", "smtp");
    properties.setProperty("mail.smtp.auth", "true");
    properties.setProperty("mail.smtp.starttls.enable", "true");
    properties.setProperty("mail.debug", "true");
    properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com");
    properties.setProperty("mail.smtp.ssl.enable", "true");
    return properties;
  }

  public JavaMailSender javaMailService() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("smtp.naver.com");
    javaMailSender.setUsername("hanjy1101@naver.com");
    javaMailSender.setPassword("L82RWSVE9HZ3");

    javaMailSender.setPort(465);

    javaMailSender.setJavaMailProperties(getMailProperties());

    return javaMailSender;
  }
}
