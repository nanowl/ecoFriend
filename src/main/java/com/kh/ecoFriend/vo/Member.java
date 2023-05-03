package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@Data
public class Member {
  private String custEmail; // 이메일
  private String custPwd; // 비밀번호
  private String custNm; // 이름
  private String custNnm; // 닉네임
  private String custGend; // 성별 M or F
  private String custPhone; // 전화번호
  private String custAddr; // 주소
  private String custCar; // 등록차량
  private int custCtp; // 등록차량의 충전타입
  private int custPtp; // 등록된 결제방법
  private Date custJoinDate; // 회원가입일
}
/**
 *  custEmail	VARCHAR2(32)	NOT NULL,
 * 	custPwd	VARCHAR2(32)		NULL,
 * 	custNm	VARCHAR2(15)		NULL,
 * 	custNnm	VARCHAR2(16)		NULL,
 * 	custGend	CHAR		NULL,
 * 	custPhone	VARCHAR2(13)		NULL,
 * 	custAddr	VARCHAR2(120)		NULL,
 * 	custCar	VARCHAR2(30)		NULL,
 * 	custCtp	NUMBER		NULL,
 * 	custPtp	NUMBER		NULL
 */