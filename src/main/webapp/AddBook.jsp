<%-- 
    Document   : AddBook
    Created on : Apr 3, 2016, 5:17:18 PM
    Author     : joshuabrown
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Book Page</title>
    </head>
    <body>
        <form method="POST" action="BookController?action=addEditDelete">
            
            <table>
                
                <tr>
                    Title:<input type="text" name="title" value="${book.title}"/>
                </tr>
                <tr>
                    Isbn:<input type="text" name="isbn" value="${book.isbn}"/>
                </tr>
                <tr>
                    
                Author: <select id="authorDropDown" name="authorId">
                <option value="">None</option>
                            <c:forEach var="author" items="${authors}">                                       
                                <option value="${author.authorId}" <c:if test="${book.authorId.authorId == author.authorId}">selected</c:if>>${author.authorName}</option>
                            </c:forEach>
                 </select> 
                    
                </tr>
                <tr>
                    <input type="submit" value="save" name="submit"/>
                </tr>
                
            </table>
            
        </form> 
    </body>
</html>
