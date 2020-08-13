/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import uyennlp.account.AccountDTO;
import uyennlp.coupon.CouponDAO;
import uyennlp.order.OrderDAO;
import uyennlp.order.OrderDTO;
import uyennlp.orderdetails.OrderDetailsDAO;
import uyennlp.orderdetails.OrderDetailsDTO;
import uyennlp.orderdetails.OrderObject;
import uyennlp.tour.TourDAO;
import uyennlp.tour.TourDTO;
import uyennlp.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class ProceedPaymentServlet extends HttpServlet {

    private final static Logger log = Logger.getLogger(ProceedPaymentServlet.class);
    private final String CHECK_OUT_PAGE = "checkout.html";
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
        String customerName = request.getParameter("txtCustomerName");
        String coupon = request.getParameter("txtCoupon");
        String totalText = request.getParameter("txtTotal");
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            ServletContext context = req.getServletContext();
            HttpSession session = request.getSession();

            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
            String customerId = account.getUserId();

            OrderObject cart = (OrderObject) session.getAttribute("CART");
            if (cart != null) {
                Map<TourDTO, Integer> list = cart.getItems();
                boolean used = false;
                if (list != null) {
                    if (!coupon.trim().isEmpty()) {
                        CouponDAO couponDAO = new CouponDAO();
                        couponDAO.setCouponUsed(coupon, customerId);
                        used = true;
                    }
                    int total = DataTypeConverter.convertStringToInteger(totalText);
                    OrderDTO dto = new OrderDTO(customerId, customerName, total, used);
                    OrderDAO orderDAO = new OrderDAO();
                    boolean check = orderDAO.addOrder(dto);
                    TourDAO tourDAO = new TourDAO();
                    if (check) {
                        int orderId = orderDAO.getLatestOrderId();
                        OrderDetailsDAO oDao = new OrderDetailsDAO();
                        for (TourDTO tour : list.keySet()) {
                            int tourId = tour.getId();
                            int quantity = list.get(tour);
                            OrderDetailsDTO oDto = new OrderDetailsDTO(orderId, tourId, quantity);
                            oDao.addOrderDetails(oDto);

                            int currentEmptySeat = tourDAO.getTourDetails(tourId).getEmptySeat();
                            tourDAO.updateTour(tourId, currentEmptySeat - quantity);
                        }
                    }
                    cart.clearCart();
                    session.removeAttribute("CART");
                    session.removeAttribute("TOTAL_BILL");
                    session.removeAttribute("CUSTOMER_NAME");
                    tourDAO.getAllTours();
                    List<TourDTO> tourList = tourDAO.getListOfTours();
                    context.setAttribute("TOURS", tourList);
                    url = CHECK_OUT_PAGE;
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
//            response.sendRedirect(url);
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
