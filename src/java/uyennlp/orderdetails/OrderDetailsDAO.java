/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.orderdetails;

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
public class OrderDetailsDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    private List<OrderDetailsDTO> detailList;

    public OrderDetailsDAO() {
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

    public List<OrderDetailsDTO> getListOfOrderDetails() {
        return detailList;
    }

    public boolean addOrderDetails(OrderDetailsDTO dto) throws SQLException, NamingException {
        boolean check = false;

        try {
            String sql = "INSERT INTO tblOrderDetails(orderId, tourId, quantity) "
                    + "VALUES(?,?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, dto.getOrderId());
            stm.setInt(2, dto.getTourId());
            stm.setInt(3, dto.getQuantity());

            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
    }
}
