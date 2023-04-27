package com.kh.ecoFriend.vo.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Autholity {
  @JsonIgnore
  private Long id;

  private String name;

  @JsonIgnore
  private MemberVO member;

  public void setMember(MemberVO member) {
    this.member = member;
  }
}
