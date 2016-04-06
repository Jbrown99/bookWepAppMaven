<%-- 

    Document   : EditBook
    Created on : Apr 3, 2016, 5:17:35 PM
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
        <title>Update a Book</title>
    </head>
    <body>
        
        
         <form method="POST" action="BookController">
            
                        
                <tr>
                   Title:<td align="left"><input type="text" value="${book.title}" name="title" /></td>
                </tr>
                <tr>
                   Isbn:<td align="left"><input type="text" value="${book.isbn}" name="isbn" /></td>
                </tr>

                <tr>
                    
                    Author:<select id="authorDropDown" name="authorId">
                    <c:choose>
                            <option value="">None</option>
                            <c:forEach var="author" items="${authors}">                                       
                                <option value="${author.authorId}" <c:if test="${book.authorId.authorId == author.authorId}">selected</c:if>>${author.authorName}</option>
                            </c:forEach>
                       
                    </c:choose>
                    </select>
                </td>
                </tr>

                <tr>
                    <input type="submit" value="Save" name="action" />
                </tr>
            </table>
        </form>
        
        
    </body>
</html>
