/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jcb.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

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
    
}
