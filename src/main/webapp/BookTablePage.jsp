<%-- 
    Document   : BookTablePage
    Created on : Apr 3, 2016, 5:16:27 PM
    Author     : joshuabrown
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <title>Book Table Page</title>
    </head>
    <body>
        
          <div class="row">
            
            
            <div class="col-sm-8">
                
        <form method="POST" action="BookController?action=addEditDelete">   
                
                <table  class="table table-hover">
            <th>Edit/Delete</th>
            <th>Title</th>
            <th>Isbn</th>
            <th>Author</th>
                <c:forEach items="${books}" var="book">
                <tr>
                    <td><input type="checkbox" name="bookId" value="${book.bookId}"/></td>  
                    <td>${book.title}</td>
                    <td>${book.isbn}</td>
                    <td>${book.author_id}</td>
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
