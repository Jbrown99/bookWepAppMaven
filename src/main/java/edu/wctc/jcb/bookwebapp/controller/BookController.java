/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jcb.bookwebapp.controller;

import edu.wctc.jcb.bookwebapp.ejb.AbstractFacade;
import edu.wctc.jcb.bookwebapp.ejb.AuthorFacade;
import edu.wctc.jcb.bookwebapp.model.Author;
import edu.wctc.jcb.bookwebapp.model.Book;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {
    private static final String BOOK_LIST="/BookTablePage.jsp";
    private static final String ADD_BOOK="/AddBook.jsp";
    private static final String UPDATE_BOOK="/UpdateBook.jsp";
    private static final String EDIT_DELETE = "addEditDelete";
    private static final String ADD = "Add";
    private static final String LIST="list";
    private static final String SAVE ="save";
    private static final String SAVE_EDIT="Save";
    private static final String DELETE ="Delete";
    private static final String SUBMIT = "submit";
    private static final String EDIT = "Edit";
    private static final String ACTION_PARAM="action";
    
    @Inject
    private AbstractFacade<Book> bookService;
      @Inject
    private AbstractFacade<Author> authService;

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
         String destination="";
        String action = request.getParameter(ACTION_PARAM);
        Book book = null;
        
        try  {
            
            
            switch (action) {
                case LIST:
                     this.refreshBookList(request,bookService);
                     this.refreshAuthorList(request,authService);
                     destination= BOOK_LIST;
                     break;
                case EDIT_DELETE:
                    String specificAction= request.getParameter(SUBMIT);
                    
                    
                    if(specificAction.equals(EDIT)){
                            String bookId=request.getParameter("book");
                             book = bookService.find(new Integer(bookId));
                            request.setAttribute("book", book);
                            destination= UPDATE_BOOK;
                    }
                    if(specificAction.equals(DELETE)){
                        String[] bookIds = request.getParameterValues("bookId");
                        for (String id : bookIds) {
                            bookService.remove(book);
                        }
                        this.refreshAuthorList(request, authService);
                        this.refreshBookList(request,bookService);
                        destination= BOOK_LIST;
                    }
                    if(specificAction.equals(ADD)){
                        this.refreshAuthorList(request, authService);
                        destination= ADD_BOOK;
                        
                    } 
                    if(specificAction.equals(SAVE)){
                    String title = request.getParameter("title");
                    String isbn = request.getParameter("isbn");
                    String authorId = request.getParameter("authorId");
                    
                    book = new Book(0);
                        book.setTitle(title);
                        book.setIsbn(isbn);
                        Author author = null;
                        if(authorId != null) {
                            author = authService.find(new Integer(authorId));
                            book.setAuthorId(author);
                        }
                    bookService.edit(book);
                    this.refreshBookList(request, bookService);
                    this.refreshAuthorList(request, authService);
                     
                    }
                    break;
                
                case SAVE_EDIT:
                    String title = request.getParameter("title");
                    String isbn = request.getParameter("isbn");
                    String authorId = request.getParameter("authorId");
                    String bookId = request.getParameter("bookId");
                     book = bookService.find(new Integer(bookId));
                        book.setTitle(title);
                        book.setIsbn(isbn);
                        Author author = null;
                        if(authorId != null) {
                            author = authService.find(new Integer(authorId));
                            book.setAuthorId(author);
                        }
                    bookService.edit(book);
                    this.refreshBookList(request, bookService);
                    this.refreshAuthorList(request, authService);
                    destination= BOOK_LIST;
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
    
     private void refreshAuthorList(HttpServletRequest request, AbstractFacade<Author> authService) throws Exception {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
    }
       private void refreshBookList(HttpServletRequest request, AbstractFacade<Book> bookService) throws Exception {
        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);
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
