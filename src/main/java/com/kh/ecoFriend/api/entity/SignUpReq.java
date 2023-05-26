package com.kh.ecoFriend.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReq {
  @ApiModelProperty(value = "이메일", example = "example@example.com", required = true)
  private String id;
  @ApiModelProperty(value = "비밀번호", example = "password123", required = true)
  private String password;
  @ApiModelProperty(value = "이름", example = "홍길동", required = true)
  private String nm;
  @ApiModelProperty(value = "성별", example = "M or F", required = true)
  private String gend;
  @ApiModelProperty(value = "전화번호", example = "010-1234-5678", required = true)
  private String phoneNumber;
  @ApiModelProperty(value = "주소", example = "서울특별시 강남구 테헤란로 11번길", required = true)
  private String addr;
}
