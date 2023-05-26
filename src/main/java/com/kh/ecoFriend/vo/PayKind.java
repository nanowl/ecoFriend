package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayKind {
  private int bankNo;
  private String email;
  private String bankNm;
  private int bankId;
}
