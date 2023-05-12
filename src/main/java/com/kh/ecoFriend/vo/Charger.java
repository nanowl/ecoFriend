package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Charger {
  private int cpId;
  private int csId;
  private String cpNm;
  private int cpTp;
  private int chargeTp;
  private int cpStat;
  private String statUpDateTime;
}
