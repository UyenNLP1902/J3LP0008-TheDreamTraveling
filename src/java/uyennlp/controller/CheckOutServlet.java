/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import uyennlp.account.AccountDTO;
import uyennlp.coupon.CouponDAO;
import uyennlp.coupon.CouponDTO;
import uyennlp.order.OrderCreateError;
import uyennlp.orderdetails.OrderObject;
import uyennlp.tour.TourDAO;
import uyennlp.tour.TourDTO;

/**
 *
 * @author HP
 */
public class CheckOutServlet extends HttpServlet {

    private final static Logger log = Logger.getLogger(CheckOutServlet.class);
    private final String CONFIRM_PAGE = "confirm.jsp";
    private final String ERROR_PAGE = "cart.jsp";

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
        String customerName = request.getParameter("txtCustomer");
        String id = request.getParameter("txtCoupon");
        OrderCreateError errors = new OrderCreateError();
        boolean foundErr = false;
        try {
            HttpSession session = request.getSession();
            OrderObject cart = (OrderObject) session.getAttribute("CART");

            if (customerName.trim().isEmpty()) {
                foundErr = true;
                errors.setCustomerNameIsEmpty("Please input your name!");
            }

            if (!id.trim().isEmpty()) {
                AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
                CouponDAO couponDAO = new CouponDAO();
                CouponDTO coupon = couponDAO.getCoupon(id);
                if (coupon == null) {
                    foundErr = true;
                    errors.setWrongCouponCode("This coupon code is not exist.");
                } else if (account != null) {
                    String userId = account.getUserId();
                    boolean used = couponDAO.isCouponUsed(id, userId);
                    if (used) {
                        foundErr = true;
                        errors.setUsedCoupon("This coupon was used");
                    }
                }
            }

            if (cart != null) {
                TourDAO tourDAO = new TourDAO();
                Map<TourDTO, Integer> list = cart.getItems();
                for (TourDTO tour : list.keySet()) {
                    if (list.get(tour) > tourDAO.getTourDetails(tour.getId()).getEmptySeat()) {
                        foundErr = true;
                        errors.setNotEnoughTour("There's not enough tour slot in stock");
                        break;
                    }
                }
            }
            
            if (!foundErr) {
                int subtotal = (int) session.getAttribute("TOTAL_BILL");
                if (!id.trim().isEmpty()) {
                    CouponDAO couponDAO = new CouponDAO();
                    CouponDTO coupon = couponDAO.getCoupon(id);
                    request.setAttribute("COUPON", coupon);
                    double discount = couponDAO.getCoupon(id).getDiscount();
                    int total = (int) (Math.ceil((double) subtotal * (100 - discount) / 100));
                    request.setAttribute("TOTAL", total);
                } else {
                    request.setAttribute("TOTAL", subtotal);
                }
                session.setAttribute("CUSTOMER_NAME", customerName);
                url = CONFIRM_PAGE;
            } else {
                request.setAttribute("ERRORS", errors);
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
