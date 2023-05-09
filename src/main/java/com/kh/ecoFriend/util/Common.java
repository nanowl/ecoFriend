package com.kh.ecoFriend.util;

import com.kh.ecoFriend.controller.ApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Common {
  private static final Logger LOGGER = LoggerFactory.getLogger(Common.class);

  // 오라클 설정 정보 (JDBC 연결)
//  @Value(value = "${spring.datasource.url}")
  private static String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521/xe";
//  @Value(value = "${spring.datasource.username}")
  private static String ORACLE_ID = "ecofriend";
//  @Value(value = "${spring.datasource.password}")
  private static String ORACLE_PW = "1234";
//  @Value(value = "${spring.datasource.driver-class-name}")
  private static String ORACLE_DRV = "oracle.jdbc.driver.OracleDriver";

  public static Connection getConnection() {
    Connection conn = null;
    LOGGER.info(ORACLE_URL + ", " + ORACLE_ID + ", " + ORACLE_PW + ", " + ORACLE_DRV);
    try {
      Class.forName(ORACLE_DRV); // 드라이버 로딩
      conn = DriverManager.getConnection(ORACLE_URL, ORACLE_ID, ORACLE_PW);
      System.out.println("Connection 연결 성공");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return conn;
  }
  public static void close(Connection conn) {
    try {
      if(conn != null && !conn.isClosed()) {
        conn.close();
        System.out.println("Connection 해제 성공");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void close(Statement stmt) {
    try {
      if(stmt != null && !stmt.isClosed()) {
        stmt.close();
        System.out.println("Statement 해제 성공");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void close(ResultSet rSet) {
    try {
      if(rSet != null && !rSet.isClosed()) {
        rSet.close();
        System.out.println("Result set 해제 성공");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
