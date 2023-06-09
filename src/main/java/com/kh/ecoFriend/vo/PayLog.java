package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayLog {
  private int payNo;
  private String email;
  private int chargeTp;
  private int chargeAmount;
  private int payment;
}
