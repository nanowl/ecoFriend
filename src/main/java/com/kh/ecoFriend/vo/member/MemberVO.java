package com.kh.ecoFriend.vo.member;

import lombok.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class MemberVO {
  private int id;
  private String email;
  private String pwd;
  private String name;
  private String nikName;
  private Gender gender; // M or F
  private String phone;
  private String addr;
  private String car;
  private int chrTp; //charge Type
  private int payTp; // pay Type
  private Date date;

  private List<Autholity> roles = new ArrayList<>();

  public void setRoles(List<Autholity> role) {
    this.roles = role;
    role.forEach(o -> o.setMember(this));
  }
}
