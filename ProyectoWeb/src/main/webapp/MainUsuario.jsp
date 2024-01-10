<%-- 
    Document   : MainUsuario
    Created on : 4 ene 2024, 19:07:59
    Author     : paser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="estilos.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%
            if ( session.getAttribute("username") != null) {
                String usuario = (String) session.getAttribute("username");
                if(usuario.equals("admin"))
                {
                    response.sendRedirect("error.jsp");
                }   
            } 
            else {
                response.sendRedirect("error.jsp");
            }
        %>
        <div class="contenedor_cabecera">
            <header>EsCineElCine</header>
            <h1>Usuario: <%=(String) session.getAttribute("username")%></h1>
            <nav class="menu">
                <ul>
                    <li><a class="option" href="Cartelera.jsp">
                            <p>Cartelera</p>
                        </a></li>
                    <li><a class="option" href="cerrarSesion.jsp">
                            <p>Cerrar Sesión</p>
                        </a></li>
                </ul>
            </nav>
        </div>
    </body>
</html>
