/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import uyennlp.util.DBHelper;
import uyennlp.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class AccountDAO implements Serializable {

    private final int USER_ROLE = 2;
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public AccountDAO() {
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

    public AccountDTO checkLogin(String userId, String password) throws NamingException, SQLException {
        AccountDTO dto = null;
        try {
            String sql = "SELECT name, role "
                    + "FROM tblAccount "
                    + "WHERE userId = ? AND password = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setString(2, password);
            rs = stm.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int role = rs.getInt("role");
                dto = new AccountDTO(userId, name, role);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean register(String userId, String name, String facebookId, String facebookLink)
            throws NamingException, SQLException {
        boolean check = false;
        try {
            String sql = "INSERT INTO tblAccount(userId, name, role, facebookId, facebookLink) "
                    + "VALUES(?,?,?,?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, facebookId);
            stm.setString(2, DataTypeConverter.convertVietnamese(name));
            stm.setInt(3, USER_ROLE);
            stm.setString(4, facebookId);
            stm.setString(5, facebookLink);
            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public AccountDTO checkLogin(String facebookId)
            throws NamingException, SQLException {
        AccountDTO dto = null;
        try {
            String sql = "SELECT userId, name, role, facebookId, facebookLink "
                    + "FROM tblAccount "
                    + "WHERE facebookId = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, facebookId);
            rs = stm.executeQuery();
            if (rs.next()) {
                String userId = rs.getString("userId");
                String name = rs.getString("name");
                int role = rs.getInt("role");
                String faceId = rs.getString("facebookId");
                String faceLink = rs.getString("facebookLink");

                dto = new AccountDTO();
                dto.setUserId(userId);
                dto.setName(name);
                dto.setRole(role);
                dto.setFacebookId(faceId.trim());
                dto.setFacebookLink(faceLink);
            }

        } finally {
            closeConnection();
        }
        return dto;
    }
}
