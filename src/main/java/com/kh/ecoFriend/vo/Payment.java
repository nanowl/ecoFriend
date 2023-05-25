package com.kh.ecoFriend.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
//@Getter
//@Setter
public class Payment {
    private String payname;
    private String email;
    private String price;
    private String credit;
    private String cardNum;
    private String endDate;
    private String cvc; // 카드 cvc넘버
}