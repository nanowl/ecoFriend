package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayBenefit {
  private int bankId;
  private String bankNm;
  private int disc;
}
