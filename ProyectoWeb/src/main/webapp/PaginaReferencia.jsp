<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="estilos.css">
        <title>Referencia</title>
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
        <% String referencia = (String) session.getAttribute("referencia"); %>
        <div class="contenedor_cabecera">
            <header>EsCineElCine</header>
            <h1>Usuario: <%=(String) session.getAttribute("username")%></h1>
            <nav class="menu">
                <ul>
                    <li><a class="option" href="CargarCartelera">
                            <p>Cartelera</p>
                        </a></li>
                    <li><a class="option" href="cerrarSesion.jsp">
                            <p>Cerrar Sesión</p>
                        </a></li>
                </ul>
            </nav>
        </div>
        <div class="contenedor_cuerpo1">
            Su referencia es: <%=referencia%>
        </div>
    </body>
</html>
