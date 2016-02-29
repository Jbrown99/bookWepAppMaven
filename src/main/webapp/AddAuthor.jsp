<%-- 
    Document   : AddAuthor
    Created on : Feb 28, 2016, 6:26:44 PM
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
        <title>Add an Author</title>
    </head>
    <body>
        <form method="POST" aciton="AuthorController">
            
            <table>
                
                <tr>
                    Name:<input type="text" name="name" value="${author.authorName}"/>
                </tr>
                
                <tr>
                    <input type="submit" value="Save" name="action"/>
                </tr>
                
            </table>
            
            
            
        </form> 
    </body>
</html>
