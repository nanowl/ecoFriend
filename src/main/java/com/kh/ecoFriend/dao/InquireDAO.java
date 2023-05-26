package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.util.Common;
import com.kh.ecoFriend.vo.Answer;
import com.kh.ecoFriend.vo.Inquire;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class InquireDAO {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    // 회원 문의 작성
    public boolean createInquire(Inquire inquire) {
        String sql = "INSERT INTO INQUIRE(CUSTEMAIL, INQTITLE, INQCONTENT) VALUES (?, ?, ?)";
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inquire.getEmail());
            pstmt.setString(2, inquire.getInqTitle());
            pstmt.setString(3, inquire.getInqContent());
            int result = pstmt.executeUpdate();
            if (result == 1) {
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


    // 문의내역 조회 메서
    public List<Inquire> getInquiresByEmail(String email) throws SQLException {
        List<Inquire> inquires = new ArrayList<>();
        String sql = "SELECT * FROM INQUIRE WHERE CUSTEMAIL = ?";
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Inquire inquire = Inquire.builder()
                        .inqNo(rs.getInt("INQNUM"))
                        .email(rs.getString("CUSTEMAIL"))
                        .inqTitle(rs.getString("INQTITLE"))
                        .inqContent(rs.getString("INQCONTENT"))
                        .isAnswered("YES".equals(rs.getString("IS_ANSWERED")))
                        .answerContent(rs.getString("ANSWER_CONTENT"))
                        .build();
                inquires.add(inquire);
            }
        } finally {
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        }
        return inquires;
    }


    // 답변 안된 문의내역조회 (답변자기준)
    public List<Inquire> getUnansweredInquiries() {
        List<Inquire> inquiries = new ArrayList<>();
        String sql = "SELECT * FROM INQUIRE WHERE IS_ANSWERED IS NULL OR IS_ANSWERED = 'NO'";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Inquire inquiry = Inquire.builder()
                        .inqNo(rs.getInt("INQNUM"))
                        .email(rs.getString("CUSTEMAIL"))
                        .inqTitle(rs.getString("INQTITLE"))
                        .inqContent(rs.getString("INQCONTENT"))
                        .isAnswered(false)
                        .answerContent(rs.getString("ANSWER_CONTENT"))
                        .build();

                inquiries.add(inquiry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        }

        return inquiries;
    }

    // 해당 문의에대한 답변자 내용업데이트
    public boolean updateInquiry(int inqNo, String answerContent) {
        boolean result = false;

        String sql = "UPDATE INQUIRE SET ANSWER_CONTENT = ?, IS_ANSWERED = 'YES' WHERE INQNUM = ?";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, answerContent);
            pstmt.setInt(2, inqNo);

            int updated = pstmt.executeUpdate();
            if (updated > 0) result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }

        return result;
    }


    // Similarly implement other CRUD operations
}

