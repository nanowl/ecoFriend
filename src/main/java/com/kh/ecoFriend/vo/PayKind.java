package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayKind {
  private int cvc;
  private String email;
  private String name;
  private String cardNo;
}
