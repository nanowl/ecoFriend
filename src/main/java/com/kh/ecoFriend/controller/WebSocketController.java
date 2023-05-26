package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.api.entity.ChatReq;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {
  private final SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/chat/{roomId}/sendMessage")
  @SendTo("/topic/{roomId}")
  public ChatReq sendMessage(@PathVariable String roomId, @Payload ChatReq chatMessage) {
    // Process and save the message
    return chatMessage;
  }
}
