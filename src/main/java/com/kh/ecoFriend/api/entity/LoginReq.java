package com.kh.ecoFriend.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
  @ApiModelProperty(value = "이메일", example = "example@example.com", required = true)
  private String email;
  @ApiModelProperty(value = "비밀번호", example = "password123", required = true)
  private String pwd;
}
