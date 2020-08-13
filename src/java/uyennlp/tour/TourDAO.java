/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.tour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import uyennlp.util.DBHelper;

/**
 *
 * @author HP
 */
public class TourDAO {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    private List<TourDTO> tourList;

    public TourDAO() {
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

    public List<TourDTO> getListOfTours() {
        return tourList;
    }

    public boolean createTour(TourDTO dto) throws SQLException, NamingException {
        boolean check = false;
        try {
            String sql = "INSERT INTO tblTour"
                    + "(name, fromDate, toDate, importDate, fromPlace, "
                    + "toPlace, price, quota, emptySeat, image, status) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getName());
            stm.setTimestamp(2, dto.getFromDate());
            stm.setTimestamp(3, dto.getToDate());
            stm.setTimestamp(4, dto.getImportDate());
            stm.setInt(5, dto.getFromPlace());
            stm.setInt(6, dto.getToPlace());
            stm.setInt(7, dto.getPrice());
            stm.setInt(8, dto.getQuota());
            stm.setInt(9, dto.getQuota());
            stm.setString(10, dto.getImage());
            stm.setBoolean(11, dto.isStatus());

            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    private String getSearchValues(int place, Timestamp fromDate, Timestamp toDate, int fromPrice, int toPrice)
            throws SQLException, NamingException {
        String searchValue = "";
        if (place > 0) {
            searchValue += "toPlace = ? AND ";
        }

        if (fromDate != null && toDate != null) {
            searchValue += "fromDate >= ? AND toDate <= ? AND ";
        } else if (fromDate != null && toDate == null) {
            searchValue += "CAST(fromDate AS DATE) >= ? AND ";
        } else if (fromDate == null && toDate != null) {
            searchValue += "CAST(toDate AS DATE) <= ? AND ";
        }

        if (fromPrice > 0 && toPrice > 0) {
            searchValue += "price BETWEEN ? AND ? AND ";
        } else if (fromPrice > 0 && toPrice == 0) {
            searchValue += "price >= ? AND ";
        } else if (fromPrice == 0 && toPrice > 0) {
            searchValue += "price <= ? AND ";
        }
        return searchValue;
    }

    public int countRecords(int place, Timestamp fromDate, Timestamp toDate, int fromPrice, int toPrice)
            throws SQLException, NamingException {
        int count = 0;
        String searchValue = getSearchValues(place, fromDate, toDate, fromPrice, toPrice);
        try {
            String sql = "SELECT COUNT(id) as NumberOfRecords "
                    + "FROM tblTour "
                    + "WHERE " + searchValue
                    + "status = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            int index = 1;

            if (place > 0) {
                stm.setInt(index, place);
                index++;
            }
            if (fromDate != null && toDate != null) {
                stm.setTimestamp(index, fromDate);
                index++;
                stm.setTimestamp(index, toDate);
                index++;
            } else if (fromDate != null && toDate == null) {
                stm.setTimestamp(index, fromDate);
                index++;
            } else if (fromDate == null && toDate != null) {
                stm.setTimestamp(index, toDate);
                index++;
            }

            if (fromPrice > 0 && toPrice > 0) {
                stm.setInt(index, fromPrice);
                index++;
                stm.setInt(index, toPrice);
                index++;
            } else if (fromPrice > 0 && toPrice == 0) {
                stm.setInt(index, fromPrice);
                index++;
            } else if (fromPrice == 0 && toPrice > 0) {
                stm.setInt(index, toPrice);
                index++;
            }
            stm.setBoolean(index, true);
            rs = stm.executeQuery();

            if (rs.next()) {
                count = rs.getInt("NumberOfRecords");
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public int searchTour(int place, Timestamp fromDate, Timestamp toDate, int fromPrice, int toPrice,
            int pageNumber, int rowsPerPage) throws SQLException, NamingException {
        int count = 0;
        String searchValue = getSearchValues(place, fromDate, toDate, fromPrice, toPrice);
        try {
            String sql = "SELECT id, name, fromDate, toDate, image, price "
                    + "FROM tblTour "
                    + "WHERE " + searchValue
                    + "status = ? "
                    + "ORDER BY fromDate ASC "
                    + "OFFSET (? - 1) * ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            int index = 1;

            if (place > 0) {
                stm.setInt(index, place);
                index++;
            }
            if (fromDate != null && toDate != null) {
                stm.setTimestamp(index, fromDate);
                index++;
                stm.setTimestamp(index, toDate);
                index++;
            } else if (fromDate != null && toDate == null) {
                stm.setTimestamp(index, fromDate);
                index++;
            } else if (fromDate == null && toDate != null) {
                stm.setTimestamp(index, toDate);
                index++;
            }

            if (fromPrice > 0 && toPrice > 0) {
                stm.setInt(index, fromPrice);
                index++;
                stm.setInt(index, toPrice);
                index++;
            } else if (fromPrice > 0 && toPrice == 0) {
                stm.setInt(index, fromPrice);
                index++;
            } else if (fromPrice == 0 && toPrice > 0) {
                stm.setInt(index, toPrice);
                index++;
            }
            stm.setBoolean(index, true);
            index++;
            stm.setInt(index, pageNumber);
            index++;
            stm.setInt(index, rowsPerPage);
            index++;
            stm.setInt(index, rowsPerPage);
            rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Timestamp fromDateX = rs.getTimestamp("fromDate");
                Timestamp toDateX = rs.getTimestamp("toDate");
                String image = rs.getString("image");
                int price = rs.getInt("price");

                TourDTO dto = new TourDTO();
                dto.setId(id);
                dto.setName(name);
                dto.setFromDate(fromDateX);
                dto.setToDate(toDateX);
                dto.setImage(image);
                dto.setPrice(price);

                if (tourList == null) {
                    tourList = new ArrayList<>();
                }
                tourList.add(dto);
                count++;
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public TourDTO getTourDetails(int id) throws SQLException, NamingException {
        TourDTO dto = null;
        try {
            String sql = "SELECT id, name, fromDate, toDate, fromPlace, "
                    + "toPlace, price, quota, emptySeat, image "
                    + "FROM tblTour "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                Timestamp fromDate = rs.getTimestamp("fromDate");
                Timestamp toDate = rs.getTimestamp("toDate");
                int fromPlace = rs.getInt("fromPlace");
                int toPlace = rs.getInt("toPlace");
                int price = rs.getInt("price");
                int quota = rs.getInt("quota");
                int emptySeat = rs.getInt("emptySeat");
                String image = rs.getString("image");

                dto = new TourDTO(id, name, fromDate, toDate, fromPlace, toPlace, price, quota, emptySeat, image);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public int getAllTours() throws SQLException, NamingException {
        int count = 0;
        try {
            String sql = "SELECT id, name, price, quota, emptySeat "
                    + "FROM tblTour";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                count++;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int quota = rs.getInt("quota");
                int emptySeat = rs.getInt("emptySeat");
                if (tourList == null) {
                    tourList = new ArrayList<>();
                }

                TourDTO dto = new TourDTO();
                dto.setId(id);
                dto.setName(name);
                dto.setPrice(price);
                dto.setQuota(quota);
                dto.setEmptySeat(emptySeat);

                tourList.add(dto);
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public boolean updateTour(int tourId, int emptySeat)
            throws SQLException, NamingException {
        boolean check = false;

        try {
            String sql = "UPDATE tblTour "
                    + "SET emptySeat = ? "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, emptySeat);
            stm.setInt(2, tourId);

            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
    }
}
