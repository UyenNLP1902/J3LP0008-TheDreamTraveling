/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uyennlp.tour.TourCreateErrors;
import uyennlp.util.DataTypeConverter;
import java.util.Date;
import java.sql.Timestamp;
import javax.naming.NamingException;
import uyennlp.tour.TourDAO;
import uyennlp.tour.TourDTO;
import org.apache.log4j.Logger;
import uyennlp.util.ValidDataChecker;

/**
 *
 * @author HP
 */
public class InsertTourServlet extends HttpServlet {

    private final static Logger log = Logger.getLogger(InsertTourServlet.class);
    private final String ERROR_PAGE = "insertTour.jsp";
    private final String SUCCESS_PAGE = "insertSuccess.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR_PAGE;
        String name = request.getParameter("txtTourName");
        String fromDateText = request.getParameter("txtFromDate");
        String toDateText = request.getParameter("txtToDate");
        String fromPlaceText = request.getParameter("cboFromPlace");
        String toPlaceText = request.getParameter("cboToPlace");
        String priceText = request.getParameter("txtPrice");
        String quotaText = request.getParameter("txtQuota");
        String imageLink = request.getParameter("txtImageLink");
        String statusText = request.getParameter("cboStatus");

        TourCreateErrors errors = new TourCreateErrors();
        boolean foundErr = false;

        try {
            if (name.trim().isEmpty()) {
                foundErr = true;
                errors.setEmptyTourName("Tour name is required.");
            }

            Date date = new Date();
            Timestamp importDate = new Timestamp(date.getTime());
            Timestamp fromDate = DataTypeConverter.convertStringToTimestamp(fromDateText);
            Timestamp toDate = DataTypeConverter.convertStringToTimestamp(toDateText);
            if (fromDate == null) {
                foundErr = true;
                errors.setInvalidFromDate("Invalid date.");
            }
            if (toDate == null) {
                foundErr = true;
                errors.setInvalidToDate("Invalid date.");
            }
            if (!toDate.after(fromDate)) {
                foundErr = true;
                errors.setSameDate("Please select different date.");
            }

            int fromPlace = DataTypeConverter.convertStringToInteger(fromPlaceText);
            int toPlace = DataTypeConverter.convertStringToInteger(toPlaceText);
            if (fromPlace == toPlace) {
                foundErr = true;
                errors.setSameDestination("Please select different place.");
            }

            int price = -1;
            if (priceText.isEmpty()) {
                foundErr = true;
                errors.setEmptyPrice("Price is required.");
            } else {
                price = DataTypeConverter.convertStringToInteger(priceText);
                if (price == -1) {
                    foundErr = true;
                    errors.setWrongPriceFormat("Price must be a positive integer.");
                }
            }

            int quota = -1;
            if (quotaText.isEmpty()) {
                foundErr = true;
                errors.setEmptyQuota("Quota is required.");
            } else {
                quota = DataTypeConverter.convertStringToInteger(quotaText);
                if (quota == -1) {
                    foundErr = true;
                    errors.setWrongQuotaFormat("Quota must be a positive integer.");
                }
            }

            if (imageLink.trim().isEmpty()
                    || !ValidDataChecker.isImageExist(imageLink)
                    ) {
                foundErr = true;
                errors.setEmptyImage("Image link is incorrect.");
            }

            boolean status;
            status = statusText.equals("1");

            if (foundErr) {
                request.setAttribute("ERRORS", errors);
            } else {
                TourDTO dto = new TourDTO(-1, name, fromDate, toDate, importDate, fromPlace, toPlace, price, quota, quota, imageLink, status);
                TourDAO dao = new TourDAO();
                boolean check = dao.createTour(dto);
                if (check) {
                    url = SUCCESS_PAGE;
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
