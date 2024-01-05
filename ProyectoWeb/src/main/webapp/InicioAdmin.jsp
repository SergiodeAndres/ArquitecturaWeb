<%-- 
    Document   : InicioAdmin
    Created on : 5 ene 2024, 12:07:23
    Author     : sergi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            if ( session.getAttribute("username") != null) {
                String usuario = (String) session.getAttribute("username");
                if(!usuario.equals("admin"))
                {
                    response.sendRedirect("error.jsp");
                }   
            } 
            else {
                response.sendRedirect("error.jsp");
            }
        %>
        <a href ="AdminSalas.jsp">Administración de salas </a>
        <br>
        <a href ="">Administración de Películas </a>
        <br>
        <a href ="">Administración de reservas </a>
        <br>
        <a href ="AdminEntradas.jsp">Administración de entradas y sesiones </a>
        <br>
        <a href ="">Informes </a>
    </body>
</html>
