
package edu.wctc.jcb.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author joshuabrown
 */
@SessionScoped
public class AuthorService implements Serializable {
    
    @Inject
    private AuthorDaoStrategy dao;
    
    public AuthorService(){
        
    }
    
    
    
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        return dao.getAuthorList();
    }
    
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException{
        return dao.deleteAuthorById(id);
    }
    
    public int updateAuthorById(String id,String authorName) throws ClassNotFoundException,SQLException{
        return dao.updateAuthorById(id,authorName);
    }
    
    public int insertIntoAuthorList(String value) throws ClassNotFoundException,SQLException{
        return dao.insertIntoAuthorList(value);
    }
    
    public Author findAuthorById(Object id)throws ClassNotFoundException,SQLException{
        return dao.findAuthorById(id);
    }
    
    

    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
    
    
    
    
    public static void main(String[] args) throws ClassNotFoundException,SQLException {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);
    }
    
}
