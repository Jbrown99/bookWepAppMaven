
package edu.wctc.jcb.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author joshuabrown
 */
public class AuthorDao implements AuthorDaoStrategy {
    private DBStrategy db = new MySqlDBStrategy();
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/book";
    private final String USER = "root";
    private final String PWD = "admin";
    
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        db.openConnection(DRIVER,URL,USER,PWD);
        
        List<Map<String,Object>> rawData =
                db.findAllRecords("author",0);
        List<Author> authors = new ArrayList<>();
        
        for(Map rec : rawData){
            Author author = new Author();
            Integer id = new Integer( rec.get("author_id").toString());
            author.setAuthorId(id);
            String name = rec.get("author_name") == null? "" : rec.get("author_name").toString();
            author.setAuthorName(name);
            Date date = rec.get("date_added") == null ? null : (Date)rec.get("date_added");
            author.setDateAdded(date);
            authors.add(author);
            
            
        }
        
        db.closeConnection();
        return authors;
    }
    
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER,URL,USER,PWD);
        
        int result = db.deleteById("author", "author_id", id);
        db.closeConnection();
        return result;
    }
    
    public int insertIntoAuthorList(List<Object> values) throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER,URL,USER,PWD);
        
         List<String> colNames = Arrays.asList("author_name","date_added");
        
        int result = db.insertRecord("Author", values,colNames);
        db.closeConnection();
        return result;
        
        
    }
    
    public int updateAuthorById(Object id,List<String> colNames,List<Object> colValues) throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER, URL, USER, PWD);
      int result = db.updateRecordById(id,colNames, colValues,"author_id",7);
      db.closeConnection();
      return result;
    
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        AuthorDaoStrategy dao = new AuthorDao();
        List<Author> authors = dao.getAuthorList();
        System.out.println(authors);
        
    }

   
    
}
