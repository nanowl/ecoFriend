package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Answer {
  private int aNo;
  private int inqNo;
  private String answer;
  private String aTitle;
  private String aContent;
}
