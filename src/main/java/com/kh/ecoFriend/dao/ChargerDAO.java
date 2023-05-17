package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.api.entity.Item;
import com.kh.ecoFriend.util.Common;
import com.kh.ecoFriend.vo.Charger;
import com.kh.ecoFriend.vo.WishStation;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChargerDAO {
  private Connection conn;
  private ResultSet rs;
  private PreparedStatement pstmt;

  public boolean setChargerDB(List<Item> itemList) {
    boolean isResult = false;
    try {
      conn = Common.getConnection();
      String sql = "INSERT INTO CHARGER(CPID, CSID, CPNM, CPTP, CHARGETP) " +
              "VALUES(?, ?, ?, ?, ?)";
      pstmt = conn.prepareStatement(sql);
      for (int i = 0; i < itemList.size(); i++) {
        Item item = itemList.get(i);
        pstmt.setInt(1, item.cpId);
        pstmt.setInt(2, item.csId);
        pstmt.setString(3, item.cpNm);
        pstmt.setInt(4, item.cpTp);
        pstmt.setInt(5, item.chargeTp);
        rs = pstmt.executeQuery();
        if (rs.next()) isResult = true;
        else isResult = false;
      }
      conn.close();
      pstmt.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return isResult;
  }
  public boolean setCharStationDB(List<Item> itemList) {
    boolean isResult = false;
    try {
      conn = Common.getConnection();
      String sql = "INSERT INTO CHARGINGSTATION(CSID, CSNM, ADDR, LNG, LAT) " +
              "VALUES(?, ?, ?, ?, ?)";
      pstmt = conn.prepareStatement(sql);
      for (int i = 0; i < itemList.size(); i++) {
        Item item = itemList.get(i);
        pstmt.setInt(1, item.csId);
        pstmt.setString(2, item.csNm);
        pstmt.setString(3, item.addr);
        pstmt.setDouble(4, item.lng);
        pstmt.setDouble(5, item.lat);
        rs = pstmt.executeQuery();
        if (rs.next()) isResult = true;
        else isResult = false;
      }
      conn.close();
      pstmt.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return isResult;
  }

  public WishStation getWishStation(String email) {
    String sql = "select * from WISHSTATION where CUSTEMAIL = ?";
    WishStation wishStation = null;
    try {
       conn = Common.getConnection();
       pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, email);
       rs = pstmt.executeQuery();
       while (rs.next()) {
         wishStation = WishStation.builder()
                 .email(rs.getString("CUSTEMAIL"))
                 .csId(rs.getInt("CSID"))
                 .build();
       }
       conn.close();
       pstmt.close();
       rs.close();
    }catch (Exception e) {
      e.printStackTrace();
    }
    return wishStation;
  }

  public boolean setWishStation(Map<String, String> data) {
    boolean result = false;
    String sql = "INSERT INTO WISHSTATION VALUES (?, ?)";
    try {
      conn = Common.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, data.get("email"));
      pstmt.setInt(2, Integer.parseInt(data.get("csId")));
      rs = pstmt.executeQuery();
      if (rs.next()) result = true;
      else result = false;
      conn.close();
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}