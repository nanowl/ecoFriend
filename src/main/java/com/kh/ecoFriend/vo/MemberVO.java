package com.kh.ecoFriend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberVO {
  private String email;
  private String pwd;
  private String name;
  private String nikName;
  private char gender; // M or F
  private String phone;
  private String addr;
  private String car;
  private int chrTp; //charge Type
  private int payTp; // pay Type
}
