
package edu.wctc.jcb.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author joshuabrown
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException;
    public int updateAuthorById(String id,String authorName) throws ClassNotFoundException, SQLException;
    public int insertIntoAuthorList(String value) throws ClassNotFoundException,SQLException;
    public Author findAuthorById(Object id)throws ClassNotFoundException,SQLException;
    
    public void initDao(String driver,String url,String user,String pwd);
    public void initDao(DataSource ds) throws SQLException;
    public DBStrategy getDb();
    public void setDb(DBStrategy db);
    public String getDriver();
    public void setDriver(String driver);
    public String getUrl();
    public void setUrl(String url);
    public String getUser();
    public void setUser(String user);
    public String getPwd();
    public void setPwd(String pwd);
    public DataSource getDs();
    public void setDs(DataSource ds);
    
}
