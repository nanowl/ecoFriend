package com.kh.ecoFriend.api.config.email;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public interface MailServiceInter {
  // Write e-Mail
  MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;

  // Email Confirm
  String createKey();

  // Send e-Mail
  String sendSimpleMessage(String to) throws Exception;
}
