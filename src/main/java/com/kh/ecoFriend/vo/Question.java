package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {
  private int typeNo;
  private String typeTitle;
  private String typeContent;
}
