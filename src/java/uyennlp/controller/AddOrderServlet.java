/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import uyennlp.orderdetails.OrderObject;
import uyennlp.tour.TourDAO;
import uyennlp.tour.TourDTO;
import uyennlp.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class AddOrderServlet extends HttpServlet {

    private final static Logger log = Logger.getLogger(AddOrderServlet.class);
    private final String ERROR_PAGE = "error.html";

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
        String tourId = request.getParameter("txtTourId");
        String quantityText = request.getParameter("txtQuantity");
        try {
            HttpSession session = request.getSession();

            OrderObject cart = (OrderObject) session.getAttribute("CART");
            if (cart == null) {
                cart = new OrderObject();
            }

            int id = DataTypeConverter.convertStringToInteger(tourId);
            TourDAO dao = new TourDAO();
            TourDTO dto = dao.getTourDetails(id);

            int quantity = DataTypeConverter.convertStringToInteger(quantityText);
            cart.addTourToCart(dto, quantity);
            int total = cart.getTotalPrice();
            session.setAttribute("TOTAL_BILL", total);
            session.setAttribute("CART", cart);

            url = "ShowTourInformationServlet?"
                    + "txtTourId=" + tourId;
//            url = "showInfo?"
//                    + "txtTourId=" + tourId;
        } catch (SQLException | NamingException ex) {
            log.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
           // response.sendRedirect(url);
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
