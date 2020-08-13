/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import uyennlp.util.DBHelper;

/**
 *
 * @author HP
 */
public class OrderDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    private List<OrderDTO> orderList;

    public OrderDAO() {
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

    public List<OrderDTO> getListOfOrders() {
        return orderList;
    }

    public boolean addOrder(OrderDTO dto) throws SQLException, NamingException {
        boolean check = false;
        try {
            String sql = "INSERT INTO tblOrder(customerId, customerName, total, couponUsed) "
                    + "VALUES(?,?,?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getCustomerId());
            stm.setString(2, dto.getCustomerName());
            stm.setInt(3, dto.getTotal());
            stm.setBoolean(4, dto.isCouponUsed());

            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public int getLatestOrderId() throws SQLException, NamingException {
        int result = -1;

        try {
            String sql = "SELECT TOP 1 id "
                    + "FROM tblOrder "
                    + "ORDER BY id DESC";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                result = rs.getInt("id");
            }
        } finally {
            closeConnection();
        }

        return result;
    }
}
