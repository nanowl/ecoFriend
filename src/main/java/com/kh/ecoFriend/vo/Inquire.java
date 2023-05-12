package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Inquire {
  private int inqNo;
  private String email;
  private String inqTitle;
  private String inqContent;
}
