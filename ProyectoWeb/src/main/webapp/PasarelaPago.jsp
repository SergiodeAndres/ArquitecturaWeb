<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="Utilitis.Sala" %>
<%@page import="Utilitis.Entrada" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Pasarela de Pago</title>
        <link rel="stylesheet" href="estilos.css">
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
        <% ArrayList<Entrada> entradas = (ArrayList) session.getAttribute("entradasSesion"); %>
        <% Sala sala = (Sala) session.getAttribute("salaActual"); %>
        <% ArrayList<String> listaAsientosReservados = (ArrayList) session.getAttribute("listaAsientosReservados"); %>
    <style>
        table {
            margin: 0 auto;
            text-align: center; /* Ajusta si el contenido de la tabla debe alinearse a la izquierda */
            margin-bottom: 20px;
        }
    </style>
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

    <div class="contenedor_cuerpo2">
        <h1><%=entradas.get(0).getNombrePelicula()%> <%=sala.getNombre()%> <%=entradas.get(0).getFecha()%> <%=entradas.get(0).getHora()%> </h1>
        <table border="1">
            <tr>
                <th>Asientos Reservados (fila_columna)</th>
                <th>Precio unitario</th>
                <th>Total</th>
            </tr>
            <tr>
                <!-- Contenido de la segunda fila, por ejemplo, datos de la reserva -->
                <td><%=listaAsientosReservados.toString()%></td>
                <td>10.00€</td>
                <td><%=listaAsientosReservados.size()*10.00%>€</td>
            </tr>
        </table>
        <form id="TarjetaFormulario" action="ServletPasarela" method="post">
            <label>Introduzca el número de su tarjeta: <input type="number" name="textoTarjeta" maxlength = "16" required></label><br>
            <input type="submit" value="Comprar">
        </form>
    </div>
    <script>
        $(document).ready(function () {
            $('#TarjetaFormulario').submit(function (event) {
                event.preventDefault();
                var formData = $(this).serialize();
                $.ajax({
                    url: 'ServletPasarela',
                    type: 'POST',
                    data: formData,
                    success: function (response) {
                        if (response.trim() !== "") {
                            alert(response);
                        } else {
                            window.location.href = 'PaginaReferencia.jsp';
                        }
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
