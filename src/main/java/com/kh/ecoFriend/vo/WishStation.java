package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishStation {
  private String custEmail;
  private int csId;
  private String addr;
  private String chargeTp;
  private String cpStat;
  private String csNm;
}
