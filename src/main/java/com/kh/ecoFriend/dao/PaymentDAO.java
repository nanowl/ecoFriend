package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.util.Common;
import com.kh.ecoFriend.vo.PayLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentDAO {
  private Connection conn;
  private ResultSet rs;
  private PreparedStatement pstmt;

  // 사용자의 결제내역을 가져온다.
  public List<PayLog> getMyPayLog(String email) {
    List<PayLog> payLogs = new ArrayList<>();
    String sql = "SELECT * FROM PAYLOG WHERE CUSTEMAIL = ?";
    try {
      conn = Common.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, email);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        PayLog payLog = PayLog.builder()
                .payNo(rs.getInt("PAYNUM"))
                .email(rs.getString("CUSTEMAIL"))
                .chargeAmount(rs.getInt("CHARGEAMOUNT"))
                .price(rs.getInt("PRICE"))
                .payDate(rs.getString("PAYDATE"))
                .build();
        payLogs.add(payLog);
      }
      conn.close();
      pstmt.close();
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return payLogs;
  }

  // 결제하기
  public boolean payPrice(Map<String, String> data) {
    int result = 0;
    String sql = "INSERT INTO PAYLOG (PAYNUM, CUSTEMAIL, CHARGEAMOUNT, PRICE, PAYDATE) VALUES (PAY_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";
    try {
      conn = Common.getConnection();
      pstmt = conn.prepareStatement(sql);
//      pstmt.setInt(1, Integer.parseInt(data.get("payNum")));
      pstmt.setString(1, data.get("email"));
      pstmt.setInt(2, Integer.parseInt(data.get("amount")));
      pstmt.setInt(3, Integer.parseInt(data.get("price")));
      result = pstmt.executeUpdate();
      conn.close();
      pstmt.close();
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (result == 1) return true;
    else return false;
  }
}
