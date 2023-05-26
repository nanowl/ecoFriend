package com.kh.ecoFriend.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicReq {
  @ApiModelProperty(value = "페이지 번호", example = "1", required = true)
  private int pageNum;
  @ApiModelProperty(value = "한 페이지에 표시할 데이터 수", example = "10", required = true)
  private int numOfRows;
  @ApiModelProperty(value = "검색할 주소", example = "서울특별시", required = true)
  private String addr;
}
