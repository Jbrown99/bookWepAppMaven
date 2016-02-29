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
    private static final String AUTHOR_LIST="/AuthorTablePage.jsp";
    private static final String ADD_AUTHOR="/AddAuthor.jsp";
    private static final String UPDATE_AUTHOR="/UpdateAuthor.jsp";
    private static final String ADD = "add";
    private static final String LIST="list";
    private static final String SAVE ="save";
    private static final String ADD_DELETE ="add/delete";
    private static final String UPDATE = "update";

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
        
        String destination="/AuthorTablePage.jsp";
        String action = request.getParameter("action");
        String specificAction= request.getParameter("submit");
        try  {
            switch (action) {
                case LIST:
                     List<Author> authors = authService.getAuthorList();
                     request.setAttribute("authors", authors);
                     destination= AUTHOR_LIST;
                     break;
                case ADD:
                    destination= ADD_AUTHOR;
                    break;
                case SAVE:
                    String authorName= request.getParameter("authorName");
                    authService.insertIntoAuthorList(authorName);
                    destination= ADD_AUTHOR;
                    break;
                case ADD_DELETE:
                    if(specificAction.equals(UPDATE)){
                        destination=UPDATE_AUTHOR;
                    }else{
                        String[] authorIds = request.getParameterValues("authorId");
                        for (String id : authorIds) {
                            authService.deleteAuthorById(id);
                        }
                    }   break;
                default:            
                    break;
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
