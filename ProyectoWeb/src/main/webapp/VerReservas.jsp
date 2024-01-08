<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.ArrayList" %>
<%@page import="Utilitis.Pelicula" %>
<%@page import="Utilitis.Sesion" %>
<%@page import="Utilitis.Sala" %>
<%@page import="Utilitis.Entrada" %>
<%@page import="Utilitis.Reserva" %>
<%@page import="Utilitis.ModeloDatos" %>
<% ModeloDatos modeloDatos = new ModeloDatos();
modeloDatos.abrirConexion();%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="estilos.css">
        <title>JSP Page</title>
    </head>
    <body>
        <nav class="menu">
            <ul>
              <li><a class="option" href="AdminSalas.jsp">
                <p>Administración de salas</p>
              </a></li>
              <li><a class="option" href="AdminCartelera.jsp">
                <p>Administración de Películas</p>
              </a></li>
              <li><a class="option" href="VerReservas.jsp">
                <p>Administración de reservas</p>
              </a></li>
              <li><a class="option" href="AdminEntradas.jsp">
                <p>Administración de entradas y sesiones</p>
              </a></li>
              <li><a class="option" href="Informes.jsp">
                <p>Informes</p>
              </a></li>
              <li><a class="option" href="cerrarSesion.jsp">
                <p>Cerrar Sesión</p>
              </a></li>
            </ul>
        </nav>
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
            ArrayList<Reserva> reservas = modeloDatos.getReservas();
        %>
        <table>
            <!-- Encabezados -->
            <tr>
                <th>Referencia</th>
                <th>Película</th>
                <th>Sala</th>
                <th>Fecha</th>
                <th>Hora</th>
                <th>Asientos</th>
            </tr>
            <!<!-- Filas -->
        <% 
            for (Reserva r : reservas) {
        %>
            <tr>
                <td><%= r.getReferencia() %></td>
                <td><%= r.getNombrePelicula() %></td>
                <td><%= r.getNombreSala() %></td>
                <td><%= r.getFecha().toString() %></td>
                <td><%= r.getHora().toString() %></td>
                <td>
                    <%
                        ArrayList<Entrada> entradas = r.getEntradas();
                        for (Entrada e : entradas) {%>
                        F<%=e.getFila()%>C<%=e.getColumna()%>
                        <%}
                     %>
                </td>
            </tr>
        <%
            }
        %>
        </table>
    </body>
</html>
