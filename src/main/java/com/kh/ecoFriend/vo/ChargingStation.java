package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChargingStation {
  private int csId;
  private String csNm;
  private String addr;
  private double lat;
  private double lng;
  private String csImgUrl;
}
