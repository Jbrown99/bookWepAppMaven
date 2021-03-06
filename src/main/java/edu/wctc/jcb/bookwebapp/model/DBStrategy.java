/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jcb.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * 
 * The general contract for all JDBC database access strategy implementations.
 * Use of this interface is mandatory
 *
 * @author joshuabrown
 */
public interface DBStrategy {
    
    public abstract void openConnection(String driverClass, String url, 
            String userName, String password) 
            throws ClassNotFoundException, SQLException;
    
    public abstract void closeConnection() throws SQLException;
    
    public abstract List<Map<String,Object>>findAllRecords(String tableName,int maxRecords) throws SQLException; 
    
    public abstract int deleteById(String tableName,String fieldName, Object value)throws SQLException;
        
     public abstract int updateRecordById(String tableName, List<String> colNames, List<Object> colValues,
            String pkColName, Object value) throws SQLException;  
      
     public abstract int insertRecord(String tableName, List<Object> colValues, List<String>colNames)throws SQLException; 
     
     public Map<String,Object> findRecordById(String tableName,Object pkColName, Object value)throws SQLException;
    
    
}
