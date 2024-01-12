<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="Utilitis.Sala" %>
<%@page import="Utilitis.Entrada" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
        <link rel="stylesheet" href="estilos.css">
        <title>Reserva de Asientos</title>
        <% ArrayList<Entrada> entradas = (ArrayList) session.getAttribute("entradasSesion"); %>
        <% Sala sala = (Sala) session.getAttribute("salaActual"); %>

</head>
<body>
    <%
            String username = (String) session.getAttribute("username");
            if(username == null || username.equals("admin"))
            {
                response.sendRedirect("error.jsp");
            }
            else
            {
        %>
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
    <div class="contenedor_pantalla">
        <h1><%=entradas.get(0).getNombrePelicula()%> <%=sala.getNombre()%> <%=entradas.get(0).getFecha()%> <%=entradas.get(0).getHora()%></h1>
        <div id="pantalla">PANTALLA</div>

            <%  %>
            <%-- Generar asientos dinámicamente (por ejemplo, 5 filas y 10 columnas) --%>
            <% for (int i = 1; i <= sala.getFilas(); i++) { %>
            <div class="fila">
                <% for (int j = 1; j <= sala.getColumnas(); j++) { %>
                <%boolean esta = false;%>
                <%for (int k = 0; k < entradas.size(); k++) { %>
                <%if (entradas.get(k).getFila() == i && entradas.get(k).getColumna() == j) {%>
                <%esta = true;%>
                <%}%>                    
                <%}%>
                <%if (esta){ %>
                <div class="asiento" id="<%= "asiento_" + i + "_" + j %>" onclick="reservarAsiento('<%= "asiento_" + i + "_" + j %>')"></div>
                <%} else {%>
                <div class="asientonodisponible" id="<%= "asiento_" + i + "_" + j %>"></div>
                <%}%>
                <% } %>
            </div>
            <% } %>
            <script>
                function reservarAsiento(idAsiento) {
                    var asiento = document.getElementById(idAsiento);
                    asiento.classList.toggle("reservado"); // Agrega o quita la clase 'reservado'
                }
            </script>
            <button class="confirmarReserva" id="confirmarReserva">
                Confirma tu Reserva
            </button>
    </div>         

    <script>
        $(document).ready(function () {
            $('#confirmarReserva').click(function () {
                var asientosReservados = document.getElementsByClassName("reservado");
                var asientosReservadosString = "";
                for (var i = 0; i < asientosReservados.length; i++) {
                    asientosReservadosString += asientosReservados[i].id + " ";
                }
                $.ajax({
                    url: 'ServletConfirmarReserva',
                    type: 'POST',
                    data: {asientosSeleccionados: asientosReservadosString},
                    success: function (response) {
                        window.location.href = 'PasarelaPago.jsp';
                    },
                    error: function () {
                        alert('Error al procesar la solicitud');
                    }
                });
            });
        });
    </script>
    <%}%>
</body>
</html>
</html>