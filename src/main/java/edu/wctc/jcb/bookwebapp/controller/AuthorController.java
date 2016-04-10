/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jcb.bookwebapp.controller;


import edu.wctc.jcb.bookwebapp.Service.AuthorService;
import edu.wctc.jcb.bookwebapp.entity.Author;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



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
   
    
    
    private AuthorService authService;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //use init parameters to config database connection
        
        
        String destination="";
        String action = request.getParameter(ACTION_PARAM);
        Author author = null;
        
        try  {
            
            
            switch (action) {
                case LIST:
                     List<Author> authors = authService.findAll();
                     request.setAttribute("authors", authors);
                     destination= AUTHOR_LIST;
                     break;
                case EDIT_DELETE:
                    String specificAction= request.getParameter(SUBMIT);
                    
                    
                    if(specificAction.equals(EDIT)){
                            String authorId=request.getParameter("authorId");
                             author = authService.find(new Integer(authorId));
                            request.setAttribute("author", author);
                            destination= UPDATE_AUTHOR;
                    }
                    if(specificAction.equals(DELETE)){
                        String[] authorIds = request.getParameterValues("authorId");
                        for (String id : authorIds) {
                            authService.remove(id);
                        }
                        this.refreshList(request, authService);
                        destination= AUTHOR_LIST;
                    }
                    if(specificAction.equals(ADD)){
                        
                        destination= ADD_AUTHOR;
                        
                    } 
                    if(specificAction.equals(SAVE)){
                        
                    String authorName = request.getParameter("authorName");
                    author = new Author(0);
                    author.setAuthorName(authorName);
                    author.setDateAdded(new Date());
                    authService.edit(author);
                    this.refreshList(request, authService);
                    destination= AUTHOR_LIST;  
                    }
                    break;
                
                case SAVE_EDIT:
                    String authorName = request.getParameter("authorName");
                    String authorId = request.getParameter("authorId");
                    author = authService.findByIdAndFetchBooksEagerly(authorId);
                        if(author == null) {
                            author = authService.findById(authorId);
                            author.setBookSet(new LinkedHashSet<>());
                        }
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
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
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
    
      public void init() throws ServletException {
        // Ask Spring for object to inject
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        authService = (AuthorService) ctx.getBean("authorService");

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
