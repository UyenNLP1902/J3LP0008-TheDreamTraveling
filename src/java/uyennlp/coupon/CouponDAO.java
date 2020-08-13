/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.coupon;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import uyennlp.util.DBHelper;

/**
 *
 * @author HP
 */
public class CouponDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public CouponDAO() {
    }

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public CouponDTO getCoupon(String id) throws SQLException, NamingException {
        CouponDTO dto = null;
        Date date = new Date();
        Timestamp today = new Timestamp(date.getTime());
        try {
            String sql = "SELECT discount, expiredDate "
                    + "FROM tblCoupon "
                    + "WHERE id = ? AND expiredDate >= ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, id);
            stm.setTimestamp(2, today);
            rs = stm.executeQuery();

            if (rs.next()) {
                double discount = rs.getDouble("discount");
                Timestamp expiredDate = rs.getTimestamp("expiredDate");
                dto = new CouponDTO(id, discount, expiredDate);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean isCouponUsed(String couponId, String userId) throws SQLException, NamingException {
        boolean check = false;
        try {
            String sql = "SELECT couponId "
                    + "FROM tblCouponUsed "
                    + "WHERE userId = ? AND couponId = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setString(2, couponId);

            rs = stm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean setCouponUsed(String couponId, String userId) throws SQLException, NamingException {
        boolean check = false;
        try {
            String sql = "INSERT INTO tblCouponUsed(userId, couponId) "
                    + "VALUES(?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setString(2, couponId);

            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
