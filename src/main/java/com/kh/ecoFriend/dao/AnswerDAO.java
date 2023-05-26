package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.util.Common;
import com.kh.ecoFriend.vo.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.*;
import java.util.*;


public class AnswerDAO {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    public boolean createAnswer(Answer answer) {
        String sql = "INSERT INTO Answers(ansNum, inqNum, answer, ansTitle, ansContent) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, answer.getANo());
            pstmt.setInt(2, answer.getInqNo());
            pstmt.setString(3, answer.getAnswer());
            pstmt.setString(4, answer.getATitle());
            pstmt.setString(5, answer.getAContent());
            int result = pstmt.executeUpdate();
            if(result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }
        return false;
    }

    // Similarly implement other CRUD operations
}
