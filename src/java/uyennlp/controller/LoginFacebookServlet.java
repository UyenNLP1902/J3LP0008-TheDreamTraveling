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
import uyennlp.account.AccountDAO;
import uyennlp.account.AccountDTO;
import uyennlp.util.APIWrapper;
import uyennlp.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class LoginFacebookServlet extends HttpServlet {
    
    private final static Logger log = Logger.getLogger(LoginServlet.class);
    private final String INVALID_PAGE = "invalid.html";
    private final String SEARCH_PAGE = "search.jsp";
    private final int USER_ROLE = 2;
//    private final String INVALID_PAGE = "invalidPage";
//    private final String SEARCH_PAGE = "searchPage";

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
        String url = INVALID_PAGE;
        try {
            String code = request.getParameter("code");
            APIWrapper wrapper = new APIWrapper();
            String accessToken = wrapper.getAccessToken(code);
            wrapper.setAccessToken(accessToken);
            
            AccountDTO dto = wrapper.getAccountInfo();
            dto.setUserId(dto.getFacebookId());
            dto.setRole(USER_ROLE);
            dto.setName(DataTypeConverter.convertVietnamese(dto.getName()));
            AccountDAO dao = new AccountDAO();
            boolean userExist = dao.checkLogin(dto.getFacebookId()) != null;
            if (!userExist) {
                dao.register(dto.getUserId(), dto.getName(), dto.getFacebookId(), dto.getFacebookLink());
            }
            
            HttpSession session = request.getSession();
            session.setAttribute("ACCOUNT", dto);
            url = SEARCH_PAGE;
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

            //response.sendRedirect(url);
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
