package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Answer {
  private int aNo;
  private int inqNo;
  private String answer;
  private String aTitle;
  private String aContent;
}
