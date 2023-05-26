package com.kh.ecoFriend.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WistStReq {
  @ApiModelProperty(value = "사용자 이메일", example = "example@example.com", required = true)
  private String email;
  @ApiModelProperty(value = "충전소 식별번호", example = "10", required = true)
  private int csId;
}
