<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Referencia</title>
    </head>
    <body>
        
        <% String referencia = (String) session.getAttribute("referencia"); %>
        
        <div>
            Su referencia es: <%=referencia%>
        </div>
    </body>
</html>
