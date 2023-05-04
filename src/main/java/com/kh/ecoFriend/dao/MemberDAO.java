package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.controller.MemberController;
import com.kh.ecoFriend.util.Common;
import com.kh.ecoFriend.vo.Member;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MemberDAO {
  private Connection conn = null;
  private ResultSet rs = null;
  private Statement stmt = null;
  private PreparedStatement pstmt = null;
  private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

  // 로그인 검증
  public boolean loginCheck(String id, String pwd) {
    try {
      conn = Common.getConnection();
      String sql = "SELECT * FROM customer WHERE CUSTEMAIL = ?";
      pstmt = conn.prepareStatement(sql); // Statement 객체 얻기
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();

//      JSONObject  jo  = new JSONObject();
//      ResultSetMetaData rmd = rs.getMetaData();

//      System.out.println(rmd.getColumnLabel(1));
      while(rs.next()) { // 읽을 데이타가 있으면 true
        String sqlId = rs.getString("CUSTEMAIL"); // 쿼리문 수행 결과에서 ID값을 가져 옴
        String sqlPwd = rs.getString("CUSTPWD");
        System.out.println("ID : " + sqlId);
        System.out.println("PWD : " + sqlPwd);

        if(id.equals(sqlId) && pwd.equals(sqlPwd)) {
          Common.close(rs);
          Common.close(pstmt);
          Common.close(conn);
          System.out.println("로그인 성공!!");
          return true;
        }
      }
      Common.close(rs);
      Common.close(pstmt);
      Common.close(conn);
    } catch(Exception e) {
      e.printStackTrace();
    }
    System.out.println("로그인 실패!!");
    return false;
  }
  // 이메일 로그인 검증
  public boolean googleLogin(String id, boolean verified) {
    try {
      conn = Common.getConnection();
      String sql = "SELECT * FROM customer WHERE CUSTEMAIL = ?";
      pstmt = conn.prepareStatement(sql); // Statement 객체 얻기
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();

//      JSONObject  jo  = new JSONObject();
//      ResultSetMetaData rmd = rs.getMetaData();

//      System.out.println(rmd.getColumnLabel(1));
      while(rs.next()) { // 읽을 데이타가 있으면 true
        String sqlId = rs.getString("CUSTEMAIL"); // 쿼리문 수행 결과에서 ID값을 가져 옴
        LOGGER.info("ID : " + sqlId);

        if(verified) {
          Common.close(rs);
          Common.close(pstmt);
          Common.close(conn);
          LOGGER.info("로그인 성공!!");
          return true;
        }
      }
      Common.close(rs);
      Common.close(pstmt);
      Common.close(conn);
    } catch(Exception e) {
      e.printStackTrace();
    }
    LOGGER.info("로그인 실패!!");
    return false;
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

      while(rs.next()) { // 읽을 데이타가 있으면 true
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
    } catch(Exception e) {
      e.printStackTrace();
    }
    return member;
  }
}
