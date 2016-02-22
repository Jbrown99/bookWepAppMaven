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
    public int updateAuthorById(Object id, List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException;
    public int insertIntoAuthorList(List<Object> values) throws ClassNotFoundException,SQLException;
    
}
