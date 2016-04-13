<%-- 
    Document   : AuthorTablePage
    Created on : Feb 6, 2016, 9:56:43 PM
    Author     : joshuabrown
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.wctc.jcb.bookwebapp.entity.Author" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <title>Author Table Page</title>
    </head>
    <body>
        <h1>We Support These Authors!</h1>
        
        
        <div class="row">
            
            
            <div class="col-sm-8">
                
        <form method="POST" action="AuthorController?action=addEditDelete">   
                
                <table  class="table table-hover">
            <th>Edit/Delete</th>
            <th>Author Name</th>
            <th>Date Added</th>
                <c:forEach items="${authors}" var="author">
                <tr>
                    <td><input type="checkbox" name="authorId" value="${author.authorId}"/></td>  
                    <td>${author.authorName}</td>
                    <td>${author.dateAdded}</td>
                </tr>
                
                
                </c:forEach>
            </table>
           
                
              
             <input type="submit" value="Add"  name="submit" class="btn btn-info" />
             <input type="submit" value="Edit" name="submit" class="btn btn-warning"/> 
             <input type="submit" value="Delete" name="submit" class="btn btn-danger"/> 
        </form>   
            </div>
        
        
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>
