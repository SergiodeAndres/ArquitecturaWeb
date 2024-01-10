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
                            <p>Administración de salas</p>
                        </a></li>
                    <li><a class="option2" href="AdminCartelera.jsp">
                            <p>Administración de Películas</p>
                        </a></li>
                    <li><a class="option2" href="VerReservas.jsp">
                            <p>Administración de reservas</p>
                        </a></li>
                    <li><a class="option2" href="AdminEntradas.jsp">
                            <p>Administración de entradas y sesiones</p>
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
        <%
            Sesion sesion = (Sesion) session.getAttribute("sesionActual");
            Sala sala = modeloDatos.getSalaNombre(sesion.getNombreSala());
            int filas = sala.getFilas();
            int columnas = sala.getColumnas();
        %>
        <div class="contenedor_cuerpo2">
            <h1>Sesión Actual</h1>
            <%= sesion.getFecha().toString() %>
            <%= sesion.getHora().toString() %>
            <%= sesion.getNombreSala() %>
            <%= sesion.getNombrePelicula() %>
            <h2>Elegir Fila: </h2> 
            <select id="seleccionFila">
                <% 
                    for(int i = 1; i <= filas; i++) {
                %>
                <option value="<%=i%>"> <%=i%> </option>
                <%
                    }
                %>
            </select>
            <h2>Elegir Columna: </h2> 
            <select id="seleccionColumna">
                <% 
                    for(int i = 1; i <= columnas; i++) {
                %>
                <option value="<%=i%>"> <%=i%> </option>
                <%
                    }
                %>
            </select>
            <br>
            <button id="Boton">Añadir Entrada</button>
            <script>
                $(document).ready(function () {
                    $('#Boton').click(function () {
                        var formData = 'modo=añadirEntrada&fila=' + $("#seleccionFila").val() + '&columna=' + $("#seleccionColumna").val();
                        $.ajax({
                            url: 'SesionesServlet',
                            type: 'POST',
                            data: formData,
                            success: function (response) {
                                if (response.trim() !== "") {
                                    alert(response);
                                } else
                                {
                                    $.ajax({
                                        url: 'SesionesServlet',
                                        type: 'POST',
                                        data: {modo: 'buscarEntradas', nombrePelicula: '<%=sesion.getNombrePelicula() %>',
                                            nombreSala: '<%=sesion.getNombreSala() %>', sesionHora: '<%=sesion.getHora().toString() %>',
                                            sesionFecha: '<%=sesion.getFecha().toString() %>'},
                                        success: function (response) {
                                            window.location.href = 'AdminEntradas.jsp';
                                        },
                                        error: function () {
                                            alert('Error al procesar la solicitud 2');
                                        }
                                    });
                                }
                                window.location.href = 'AdminEntradas.jsp';
                            },
                            error: function () {
                                alert('Error al procesar la solicitud');
                            }
                        });
                    });
                });
            </script>
        </div>
    </body>
</html>