/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import uyennlp.tour.TourDAO;
import uyennlp.tour.TourDTO;
import uyennlp.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class SearchServlet extends HttpServlet {

    private final static Logger log = Logger.getLogger(SearchServlet.class);
    private final String SEARCH_PAGE = "search.jsp";
    private final int ROWS_PER_PAGE = 20;

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
        String url = SEARCH_PAGE;
        String searchPlace = request.getParameter("cboSearchPlace");
        String fromDateText = request.getParameter("txtFromDate");
        String toDateText = request.getParameter("txtToDate");
        String priceFromText = request.getParameter("txtPriceFrom");
        String priceToText = request.getParameter("txtPriceTo");
        String page = request.getParameter("txtCurrentPage");
        try {
            if (searchPlace.equals("0")
                    && fromDateText.isEmpty()
                    && toDateText.isEmpty()
                    && priceFromText.equals("0")
                    && priceToText.equals("0")) {
                
            } else {
                int place = DataTypeConverter.convertStringToInteger(searchPlace);

                Timestamp fromDate = null, toDate = null;
                if (!fromDateText.isEmpty()) {
                    fromDate = DataTypeConverter.convertStringToTimestamp(fromDateText);
                }
                if (!toDateText.isEmpty()) {
                    toDate = DataTypeConverter.convertStringToTimestamp(toDateText);
                }

                int fromPrice = DataTypeConverter.convertStringToInteger(priceFromText);
                int toPrice = DataTypeConverter.convertStringToInteger(priceToText);

                int pageNumber = DataTypeConverter.convertStringToInteger(page);

                TourDAO dao = new TourDAO();
                dao.searchTour(place, fromDate, toDate, fromPrice, toPrice, pageNumber, ROWS_PER_PAGE);
                List<TourDTO> list = dao.getListOfTours();
                request.setAttribute("RESULTS", list);

                int noOfRecords = dao.countRecords(place, fromDate, toDate, fromPrice, toPrice);
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ROWS_PER_PAGE);
                request.setAttribute("noOfPages", noOfPages);
                request.setAttribute("currentPage", pageNumber);
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
