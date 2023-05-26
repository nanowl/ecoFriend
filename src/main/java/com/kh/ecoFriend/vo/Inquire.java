package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Inquire {
  private int inqNo;
  private String email;
  private String inqTitle;
  private String inqContent;
  private boolean isAnswered;
  private String answerContent;
}
