<%-- 
    Document   : AuthorAddUpdate
    Created on : Feb 24, 2016, 6:46:01 PM
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
        <title>Update an Author</title>
    </head>
    <body>
        
        <form method="POST" action="AuthorController">
            
            <Table>
                
                <tr>
                    <input type="text" value="${author.authorId}" name="authorId" readonly/>
                    <input type="text" value="${author.authorName}" name="authorName"/>
                    <input type="text" value="${author.dateAdded}" name="dateAdded" readonly/>
                </tr>
                
                <tr>
                    <input type="submit" value="Save" name="action"/>
                </tr>
                
                
                
            </Table>
            
            
            
            
            
        </form>
        
        
        
    </body>
</html>
