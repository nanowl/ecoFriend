package com.kh.ecoFriend.api.entity;

import lombok.Data;

@Data
public class ChatReq {
  private Integer channelId;
  private String writerEmail;
  private String chat;
}
