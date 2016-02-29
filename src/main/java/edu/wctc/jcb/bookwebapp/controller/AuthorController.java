/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jcb.bookwebapp.controller;

import edu.wctc.jcb.bookwebapp.model.Author;
import edu.wctc.jcb.bookwebapp.model.AuthorService;
import edu.wctc.jcb.bookwebapp.model.MockAuthorDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author joshuabrown
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
     */
    private String driverClass;
    private String url;
    private String userName;
    private String pwd;
    
    @Inject
    private AuthorService authService;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //use init parameters to config database connection
        configDbConnection();
        
        String destination="/authorTablePage";
        String action = request.getParameter("action");
        String specificAction= request.getParameter("submit");
        try  {
            
            if(action.equals("add") ){
                
                destination= "/AddAuthor.jsp";
            }
            if(action.equals("save")){
                String authorName= request.getParameter("authorName");
                authService.insertIntoAuthorList(authorName);
                destination="/AddAuthor.jsp";
            }
            if(action.equals("add/delete")){
               
                if(specificAction.equals("update")){
                destination="/UpdateAuthor.jsp";
            }else{
                  String[] authorIds = request.getParameterValues("authorId");
                        for (String id : authorIds) {
                            authService.deleteAuthorById(id);  
                         }       
                }            
            }            
                        
            
        } catch(Exception e){
            request.setAttribute("errorMsg", e.getMessage());
        }
          RequestDispatcher view =
                    request.getRequestDispatcher(destination);
            view.forward(request,response);  
            
        }
    
    
    
    private void configDbConnection(){
        authService.getDao().initDao(driverClass, url, userName, pwd);
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

    @Override
    public void init() throws ServletException{
        driverClass = getServletContext().getInitParameter("db.driver.class");
        url = getServletContext().getInitParameter("db.url");
        userName = getServletContext().getInitParameter("db.username");
        pwd = getServletContext().getInitParameter("db.password");
    }
    
}
