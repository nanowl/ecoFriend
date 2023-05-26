package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.util.Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Map;


@Repository
public class PaymentDAO {
    private Connection conn = null;
    private ResultSet rs = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    // 금액 충전
    public boolean chargeMoney(String payname, String email, String credit, String cardNum, String endDate, String cvc, String price) {
        int result = 0;
        String paySql = "INSERT INTO PAYMENTS(PAYNAME, EMAIL, CREDIT, CARDNUM, ENDDATE, CVC, PRICE) " +
                "VALUES(?, ? , ?, ?, ?, ?, ?)";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(paySql);
            pstmt.setString(1, payname);
            pstmt.setString(2, email);
            pstmt.setString(3, credit);
            pstmt.setString(4, cardNum);
            pstmt.setString(5, endDate);
            pstmt.setString(6, cvc);
            pstmt.setString(7, price);
            System.out.println("회원 가입 DB 결과 확인 : " + result);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(pstmt);
            Common.close(conn);

            if(result > 0) return true;
            else return false;
        }



    }



    // 충전량 조회

    public  int getChargeMoney(String email) {
        String query = "SELECT SUM(PRICE) AS total FROM PAYMENTS WHERE EMAIL = ?";
        int total = 0;

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }

        return total;
    }



}