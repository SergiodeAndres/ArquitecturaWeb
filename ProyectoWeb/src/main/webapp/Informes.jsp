<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.ArrayList" %>
<%@page import="Utilitis.Pelicula" %>
<%@page import="Utilitis.Sesion" %>
<%@page import="Utilitis.Sala" %>
<%@page import="Utilitis.Entrada" %>
<%@page import="Utilitis.ModeloDatos" %>
<% ModeloDatos modeloDatos = new ModeloDatos();
modeloDatos.abrirConexion();%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <div class="contenedor_cabecera">
            <header>EsCineElCine</header>
            <h1>Usuario: Administrador</h1>
            <nav class="menu">
                <ul>
                    <li><a class="option2" href="AdminSalas.jsp">
                            <p>Salas</p>
                        </a></li>
                    <li><a class="option2" href="AdminCartelera.jsp">
                            <p>Películas</p>
                        </a></li>
                    <li><a class="option2" href="VerReservas.jsp">
                            <p>Reservas</p>
                        </a></li>
                    <li><a class="option2" href="AdminEntradas.jsp">
                            <p>Entradas y sesiones</p>
                        </a></li>
                    <li><a class="option2" href="Informes.jsp">
                            <p>Informes</p>
                        </a></li>
                    <li><a class="option2" href="cerrarSesion.jsp">
                            <p>Cerrar Sesión</p>
                        </a></li>
                </ul>
            </nav>
        </div>
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
        <div class="contenedor_cuerpo2">
            <h2>Ver Películas por (Categoría): </h2> 
            <button id="Genero">Género</button>
            <button id="Sala">Sala</button>
            <script>
                $(document).ready(function () {
                    $('#Genero').click(function () {
                        var formData = 'modo=buscarGeneros';
                        $.ajax({
                            url: 'InformesServlet',
                            type: 'POST',
                            data: formData,
                            success: function (response) {
                                window.location.href = 'Informes.jsp';
                            },
                            error: function () {
                                alert('Error al procesar la solicitud');
                            }
                        });
                    });
                    $('#Sala').click(function () {
                        var formData = 'modo=buscarSalas';
                        $.ajax({
                            url: 'InformesServlet',
                            type: 'POST',
                            data: formData,
                            success: function (response) {
                                window.location.href = 'Informes.jsp';
                            },
                            error: function () {
                                alert('Error al procesar la solicitud');
                            }
                        });
                    });
                });
            </script>
            <%
            if (session.getAttribute("contenidoListaInforme") != null)
            {
                ArrayList<String> contenidos = (ArrayList<String>) session.getAttribute("contenidoListaInforme");
            %>
            <select id="seleccionInforme">
                <% 
                    for(String s: contenidos) {
                %>
                <option value="<%=s%>"> <%=s%> </option>
                <%
                    }
                %>
            </select>
            <button id="BotonBuscar">Ver Películas</button>
            <script>
                $(document).ready(function () {
                    $('#BotonBuscar').click(function () {
                        $.ajax({
                            url: 'InformesServlet',
                            type: 'POST',
                            data: {modo: 'verInforme', busqueda: $("#seleccionInforme").val()},
                            success: function (response) {
                                window.location.href = 'Informes.jsp';
                            },
                            error: function () {
                                alert('Error al procesar la solicitud');
                            }
                        });
                    });
                });
            </script>
            <br>
            <%
            }
            %>
            <%
                if (session.getAttribute("peliculasInforme") != null)
                {
                    ArrayList<String> peliculas = (ArrayList<String>) session.getAttribute("peliculasInforme");
                    for (String p: peliculas) {%>
            <%= p %>
            <br>
            <%}
            %>

            <%}
            %>
        </div>
    </body>
</html>
