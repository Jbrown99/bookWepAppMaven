<%-- 
    Document   : AuthorTablePage
    Created on : Feb 6, 2016, 9:56:43 PM
    Author     : joshuabrown
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Author" %>
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
                
                <table  class="table table-hover">
            
            <th>Author Id</th>
            <th>Author Name</th>
            <th>Date Added</th>
                <c:forEach items="${requestScope.authorList}" var="author">
                <tr>
                    <td>${author.authorId}</td>
                    <td>${author.authorName}</td>
                    <td>${author.dateAdded}</td>
                </tr>
                
                
                </c:forEach>
            </table>
            
            </div>
        
        
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>
