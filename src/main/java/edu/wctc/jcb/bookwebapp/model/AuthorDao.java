
package edu.wctc.jcb.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author joshuabrown
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy,  Serializable {
    
    @Inject
    private DBStrategy db;
    
    private  String driver;
    private  String url;
    private  String user;
    private  String pwd;
    
    public AuthorDao(){
        
    }
    public void initDao(String driver,String url,String user,String pwd){
        setDriver(driver);
        setUrl(url);
        setUser(user);
        setPwd(pwd);
    }
    
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        db.openConnection(driver,url,user,pwd);
        
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
    
    public Author findAuthorById(Object id) throws ClassNotFoundException,SQLException{
        db.openConnection(driver, url, user, pwd);
        
        Map<String,Object> result = db.findRecordById("author","author_id",id);
        Author author = new Author();
        
        author.setAuthorId((Integer)result.get("author_id"));
        author.setAuthorName(result.get("author_name").toString());
        author.setDateAdded((Date)result.get("date_added"));
        
        
        db.closeConnection();
        
        return author;
        
    } 
    
    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException{
        db.openConnection(driver,url,user,pwd);
        
        int result = db.deleteById("author", "author_id", id);
        db.closeConnection();
        return result;
    }
    
    @Override
    public int insertIntoAuthorList(String authorName) throws ClassNotFoundException, SQLException{
        db.openConnection(driver,url,user,pwd);
        
         List<String> colNames = Arrays.asList("author_name","date_added");
         List<Object> values = Arrays.asList(authorName,new Date());
        int result = db.insertRecord("Author", values,colNames);
        db.closeConnection();
        return result;
        
        
    }
    
    public int updateAuthorById(String id, List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException{
        db.openConnection(driver, url, user, pwd);
      int result = db.updateRecordById(id,colNames, colValues,"author_id",7);
      db.closeConnection();
      return result;
    
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    
    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }
    
    
    
    
    
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        
//        AuthorDaoStrategy dao;
//        List<Author> authors = dao.getAuthorList();
//        System.out.println(authors);
//        
//    }

    @Override
    public int updateAuthorById(Object id, List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Author> findAuthorById() throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
   
    
}
