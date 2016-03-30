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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



/**
 *
 * @author joshuabrown
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {
    private static final String AUTHOR_LIST="/AuthorTablePage.jsp";
    private static final String ADD_AUTHOR="/AddAuthor.jsp";
    private static final String UPDATE_AUTHOR="/UpdateAuthor.jsp";
    private static final String EDIT_DELETE = "addEditDelete";
    private static final String ADD = "Add";
    private static final String LIST="list";
    private static final String SAVE ="save";
    private static final String SAVE_EDIT="Save";
    private static final String DELETE ="Delete";
    private static final String SUBMIT = "submit";
    private static final String EDIT = "Edit";
    private static final String ACTION_PARAM="action";
   

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
    private String dbJndiName;
    
    @Inject
    private AuthorService authService;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //use init parameters to config database connection
        
        
        String destination="";
        String action = request.getParameter(ACTION_PARAM);
        
        try  {
            configDbConnection();
            
            switch (action) {
                case LIST:
                     List<Author> authors = authService.getAuthorList();
                     request.setAttribute("authors", authors);
                     destination= AUTHOR_LIST;
                     break;
                case EDIT_DELETE:
                    String specificAction= request.getParameter(SUBMIT);
                    
                    
                    if(specificAction.equals(EDIT)){
                            String authorId=request.getParameter("authorId");
                            Author author = authService.findAuthorById(authorId);
                            request.setAttribute("author", author);
                            destination= UPDATE_AUTHOR;
                    }
                    if(specificAction.equals(DELETE)){
                        String[] authorIds = request.getParameterValues("authorId");
                        for (String id : authorIds) {
                            authService.deleteAuthorById(id);
                        }
                        this.refreshList(request, authService);
                        destination= AUTHOR_LIST;
                    }
                    if(specificAction.equals(ADD)){
                        
                        destination= ADD_AUTHOR;
                        
                    } 
                    if(specificAction.equals(SAVE)){
                        
                    String authorName = request.getParameter("authorName");
                    authService.insertIntoAuthorList(authorName);
                    this.refreshList(request, authService);
                    destination= AUTHOR_LIST;  
                    }
                    break;
                
                case SAVE_EDIT:
                    String authorName = request.getParameter("authorName");
                    String authorId = request.getParameter("authorId");
                    authService.updateAuthorById(authorId, authorName);
                    this.refreshList(request, authService);
                    destination= AUTHOR_LIST;
                    break;
                    
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
    
     private void refreshList(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = authService.getAuthorList();
        request.setAttribute("authors", authors);
    }
    
    private void configDbConnection() throws NamingException, SQLException{
        
        if(dbJndiName == null) {
            authService.getDao().initDao(driverClass, url, userName, pwd);   
        } else {
            /*
             Lookup the JNDI name of the Glassfish connection pool
             and then use it to create a DataSource object.
             */
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dbJndiName);
            authService.getDao().initDao(ds);
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

    @Override
    public void init() throws ServletException{
        //driverClass = getServletContext().getInitParameter("db.driver.class");
        //url = getServletContext().getInitParameter("db.url");
        //userName = getServletContext().getInitParameter("db.username");
        //pwd = getServletContext().getInitParameter("db.password");
        dbJndiName = getServletContext().getInitParameter("db.jndi.name");
    }
    
}
