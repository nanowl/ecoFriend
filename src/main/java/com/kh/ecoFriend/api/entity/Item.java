package com.kh.ecoFriend.api.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Item {
  public String addr;
  public int chargeTp;
  public int cpId;
  public String cpNm;
  public int cpStat;
  public int cpTp;
  public int csId;
  public String csNm;
  public double lat;
  public double lng;
  public String statUpdateDatetime;
}
