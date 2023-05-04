package com.kh.ecoFriend.api.config.email;

public interface EmailService {
  String sendSimpleMessage(String to)throws Exception;
}
