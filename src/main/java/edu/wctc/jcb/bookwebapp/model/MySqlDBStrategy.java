/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jcb.bookwebapp.model;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;

/**
 *
 * @author joshuabrown
 */
@Dependent
public class MySqlDBStrategy implements DBStrategy, Serializable {
    
    private Connection conn;
    
    public MySqlDBStrategy(){
        
    }
    
    @Override
    public void openConnection(String driverClass, String url, 
            String userName, String password) throws ClassNotFoundException, SQLException {
        
        Class.forName (driverClass);
        conn = DriverManager.getConnection(url,userName, password);
        
    }
    
    public  void closeConnection() throws SQLException {
        conn.close();
    }
    
    
    @Override
    public int updateRecordById(String tableName, List<String> colNames, List<Object> colValues,String pkColName, Object value) throws SQLException{
        
        
         PreparedStatement pstmt = null;
        int recsUpdated = 0;

        // do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        try {
                    pstmt = buildUpdateStatement(conn,tableName,colNames,pkColName);

                    final Iterator i=colValues.iterator();
                    int index = 1;
                    Object obj = null;

                    // set params for column values
                    while( i.hasNext()) {
                        obj = i.next();
                        pstmt.setObject(index++, obj);
                    }
                    // and finally set param for wehere value
                    pstmt.setObject(index,value);
                    
                    recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
                    try {
                            pstmt.close();
                            conn.close();
                            
                    } catch(SQLException e) {
                            throw e;
                    } // end try
        } // end finally

        return recsUpdated;
        
        
        
    }
    
    public List<Map<String,Object>>findAllRecords(String tableName, int maxRecords) throws SQLException{
        
        String sql;
        if(maxRecords < 1){
            sql = "select * from " + tableName;
        }else {
            sql= "select * from " + tableName + " limit " + maxRecords;
        }
        
        Statement smt = conn.createStatement();
        ResultSet rs = smt.executeQuery(sql);
        
        ResultSetMetaData rsmd = rs.getMetaData();
        
        int columnCount = rsmd.getColumnCount();
        
        List<Map<String,Object>> records = new ArrayList<>();
        
        while(rs.next()){
            Map<String,Object> record = new HashMap<>();
            
            for(int colNo =1; colNo <= columnCount; colNo++){
                
                Object colData = rs.getObject(colNo);
                
                String colName = rsmd.getColumnName(colNo);
                
                record.put(colName,colData);
            }
            
            records.add(record);
            
        }
        
        return records;
        
        
    }
    
    @Override
    public Map<String,Object> findRecordById(String tableName,Object pkColName, Object value)throws SQLException{
        //select * from author where author_id = 1
        String sql = "SELECT * FROM " + tableName + " where " + pkColName + " = ?";
        
        PreparedStatement psmt = null;
        Map<String,Object>record = new HashMap();
        
        try{
            psmt=conn.prepareStatement(sql);
            psmt.setObject(1,value);
            ResultSet rs = psmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            
            while(rs.next()){
                
                 for(int colNo =1; colNo <= columnCount; colNo++){
                     record.put(rsmd.getColumnName(colNo), rs.getObject(colNo));
                 }
                
            }
            
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
                    try {
                            psmt.close();
                            conn.close();
                            
                    } catch(SQLException e) {
                            throw e;
                    } // end try
        } // end finally
            
            return record;
            
            
        }
        
    
    
    public int deleteById(String tableName,String pkColName, Object value)throws SQLException{
        
        
        
        
        String  deleteString = "DELETE FROM " + tableName+ " where "
                + pkColName + " = ?"; 
        
        PreparedStatement psmt= conn.prepareStatement(deleteString);
            
            psmt.setObject(1,value);
            return psmt.executeUpdate();
        
        
        
        
    }
    
    public int insertRecord(String tableName,List<Object>colValues,List<String>colNames)throws SQLException{
        
        PreparedStatement psmt = null;
        int recordsInserted = 0;
        
        try{
            
            psmt = buildInsertStatement(conn,tableName, colNames);
        
            
                        final Iterator i =colValues.iterator();
                        int index = 1;
                        while (i.hasNext()){
                            final Object obj = i.next();
                            psmt.setObject(index++, obj);
                        }
                        
                       
                    recordsInserted = psmt.executeUpdate();
        
       
         
               } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
                    try {
                            psmt.close();
                            conn.close();
                            
                    } catch(SQLException e) {
                            throw e;
                    } // end try
        } // end finally

        return recordsInserted;
        
        
    }
    
    /*
	 * Builds a java.sql.PreparedStatement for an sql update using only one where clause test
	 * @param conn - a JDBC <code>Connection</code> object
	 * @param tableName - a <code>String</code> representing the table name
	 * @param colDescriptors - a <code>List</code> containing the column descriptors for
	 * the fields that can be updated.
	 * @param whereField - a <code>String</code> representing the field name for the
	 * search criteria.
	 * @return java.sql.PreparedStatement
	 * @throws SQLException
	 */
	private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName,
												   List colDescriptors, String whereField)
	throws SQLException {
		StringBuffer sql = new StringBuffer("UPDATE ");
		(sql.append(tableName)).append(" SET ");
		final Iterator i=colDescriptors.iterator();
		while( i.hasNext() ) {
			(sql.append( (String)i.next() )).append(" = ?, ");
		}
		sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) );
		((sql.append(" WHERE ")).append(whereField)).append(" = ?");
		final String finalSQL=sql.toString();
		return conn_loc.prepareStatement(finalSQL);
	}
        
        private PreparedStatement buildInsertStatement(Connection conn_loc,String tableName, List colValues)throws SQLException{
            StringBuffer sql = new StringBuffer("INSERT INTO ");
            (sql.append(tableName)).append(" Values ( ");
            final Iterator i =colValues.iterator();
            while( i.hasNext() ){
                (sql.append( (String)i.next() )).append(" ?, ");
            }
            sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ")));
            sql.append(" );");
            final String finalSQL = sql.toString();
            return conn_loc.prepareStatement(finalSQL);
            
        }
    
    

    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
         DBStrategy db = new MySqlDBStrategy();
         
//         db.openConnection("com.mysql.jdbc.Driver",
//                 "jdbc:mysql://localhost:3306/book",
//                 "root",
//                 "admin");
//         
//         
//       List<Map<String,Object>> rawData =
//                db.findAllRecords("author",0);
//                 System.out.println(rawData);


//            List<Object> rawData = db.findRecordById("author","author_id",1);
//            System.out.println(rawData);
//                 
//          db.closeConnection();
         
        // int result = db.deleteById("author", "author_id", 2);
        
//         db.openConnection("com.mysql.jdbc.Driver",
//                 "jdbc:mysql://localhost:3306/book",
//                 "root",
//                 "admin");
//                 
//        List<String> colNames = Arrays.asList("author_name", "date_added");
//        List<Object> colValues = Arrays.asList("Lucifer","2002-02-11");
//        int result = db.updateRecordById("author", colNames, colValues, "author_id", 1);
//        
//        db.closeConnection();
//        
        
//        db.openConnection("com.mysql.jdbc.Driver",
//                 "jdbc:mysql://localhost:3306/book",
//                 "root",
//                 "admin");
//                
//        List<Map<String,Object>> rawData = db.findAllRecords("author",0);
//                 System.out.println(rawData);
//                 
//                 db.closeConnection();


           db.openConnection("com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/book",
                    "root",
                    "admin");
           
           List<Object> colValues = Arrays.asList("Josh","1990-04-11");
           List<String> colNames = Arrays.asList("author_name","date_added");
           
           
           
           int result = db.insertRecord("author",colValues,colNames);
           
           System.out.println(result);
           
           
           db.closeConnection();
           

    }

    
    
    
}
