package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishStation {
  private String email;
  private int csId;
}
