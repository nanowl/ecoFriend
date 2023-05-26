package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.api.entity.SignUpReq;
import com.kh.ecoFriend.controller.MemberController;
import com.kh.ecoFriend.util.Common;
import com.kh.ecoFriend.vo.Member;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberDAO {
  private Connection conn = null;
  private ResultSet rs = null;
  private Statement stmt = null;
  private PreparedStatement pstmt = null;
  private static final Logger LOGGER = LoggerFactory.getLogger(MemberDAO.class);

  // 로그인 검증
  public boolean loginCheck(String id, String pwd) {
    boolean isLogin = false;
    try {
      conn = Common.getConnection();
      String sql = "SELECT * FROM customer WHERE CUSTEMAIL = ?";
      pstmt = conn.prepareStatement(sql); // Statement 객체 얻기
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();

      while (rs.next()) { // 읽을 데이타가 있으면 true
        String sqlId = rs.getString("CUSTEMAIL"); // 쿼리문 수행 결과에서 ID값을 가져 옴
        String sqlPwd = rs.getString("CUSTPWD");
        System.out.println("ID : " + sqlId);
        System.out.println("PWD : " + sqlPwd);

        if (id.equals(sqlId) && pwd.equals(sqlPwd)) {
          System.out.println("로그인 성공!!");
          isLogin = true;
        }
      }
      Common.close(rs);
      Common.close(pstmt);
      Common.close(conn);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return isLogin;
  }
  // 이메일 로그인 검증
  public boolean googleLogin(String id) {
    boolean isLogin = false;
    try {
      conn = Common.getConnection();
      String sql = "SELECT * FROM customer WHERE CUSTEMAIL = ?";
      pstmt = conn.prepareStatement(sql); // Statement 객체 얻기
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();

      while (rs.next()) { // 읽을 데이타가 있으면 true
        LOGGER.info("로그인 성공!!");
        isLogin = true;
      }
      Common.close(rs);
      Common.close(pstmt);
      Common.close(conn);
    } catch (Exception e) {
      e.printStackTrace();
    }
    LOGGER.info("로그인 실패!!");
    return isLogin;
  }
  // 로그인 후 응답할 객체정보
  public Member getMemberData(String id) {
    Member member = Member.builder().build();
    try {
      conn = Common.getConnection();
      String sql = "SELECT * FROM CUSTOMER WHERE CUSTEMAIL = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();

      while (rs.next()) { // 읽을 데이타가 있으면 true
        member.setCustEmail(rs.getString("CUSTEMAIL"));
        member.setCustPwd(rs.getString("CUSTPWD"));
        member.setCustNm(rs.getString("CUSTNM"));
        member.setCustNnm(rs.getString("CUSTNNM"));
        member.setCustGend(rs.getString("CUSTGEND"));
        member.setCustPhone(rs.getString("CUSTPHONE"));
        member.setCustAddr(rs.getString("CUSTADDR"));
        member.setCustCar(rs.getString("CUSTCAR"));
        member.setCustCtp(rs.getInt("CUSTCTP"));
        member.setCustPtp(rs.getInt("CUSTPTP"));
        member.setCustJoinDate(rs.getDate("CUSTJOINDATE"));
      }
      Common.close(rs);
      Common.close(pstmt);
      Common.close(conn);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return member;
  }
  // 중복확인
  public boolean getCheckEmail(String id) {
    try {
      conn = Common.getConnection();
      String sql = "SELECT * FROM CUSTOMER WHERE CUSTEMAIL = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();

      while (rs.next()) { // 읽을 데이타가 있으면 true
        return false;
      }
      Common.close(rs);
      Common.close(pstmt);
      Common.close(conn);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }
  // 회원가입
  public boolean signUpUserData(SignUpReq data) {
    int result = 0;
    String sql = "INSERT INTO customer(custEmail, custPwd, custNm, custGend, custPhone, custAddr, custJoinDate) " +
            "VALUES(?, ?, ?, ?, ?, ?, SYSDATE)";
    try {
      conn = Common.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, data.getId());
      pstmt.setString(2, data.getPassword());
      pstmt.setString(3, data.getNm());
      pstmt.setString(4, data.getGend());
      pstmt.setString(5, data.getPhoneNumber());
      pstmt.setString(6, data.getAddr());
      result = pstmt.executeUpdate();
      System.out.println("회원 가입 DB 결과 확인 : " + result);

    } catch (Exception e) {
      e.printStackTrace();
    }
    Common.close(pstmt);
    Common.close(conn);

    if(result == 1) return true;
    else return false;

  }
}
