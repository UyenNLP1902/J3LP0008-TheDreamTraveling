/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.place;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import uyennlp.util.DBHelper;

/**
 *
 * @author HP
 */
public class PlaceDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    private List<PlaceDTO> placeList;

    public PlaceDAO() {
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

    public List<PlaceDTO> getListOfPlaces() {
        return placeList;
    }

    public int getAllPlaces() throws SQLException, NamingException {
        int count = 0;
        try {
            String sql = "SELECT id, destination "
                    + "FROM tblPlace";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String destination = rs.getString("destination");
                PlaceDTO dto = new PlaceDTO(id, destination);

                if (placeList == null) {
                    placeList = new ArrayList<>();
                }
                count++;
                placeList.add(dto);
            }
        } finally {
            closeConnection();
        }
        return count;
    }
}
