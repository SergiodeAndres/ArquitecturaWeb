<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="Utilitis.Sala" %>
<%@page import="Utilitis.Entrada" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reserva de Asientos</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
            margin: 20px 0 0;
        }

        #pantalla {
            width: 20%;
            height: 10px;
            background-color: #808080;
            margin-bottom: 20px;
        }

        .fila {
            display: flex;
            margin-bottom: 10px;
        }

        .asiento {
            width: 20px;
            height: 20px;
            background-color: #00ff00;
            margin-right: 5px;
            cursor: pointer;
        }
        .asiento.reservado {
            background-color: #0000ff; /* Cambia el color a azul para asientos reservados */
        }
        .asientonodisponible{
            width: 20px;
            height: 20px;
            background-color: #00ff00;
            margin-right: 5px;
            background-color: #ff0000;
        }
    </style>
</head>
    <body>
        
        <% ArrayList<Entrada> entradas = (ArrayList) session.getAttribute("entradasSesion"); %>
        <% Sala sala = (Sala) session.getAttribute("salaActual"); %>
        <div id="pantalla"></div>
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
        <button class="confirmarReserva" id="confirmarReserva" onclick="mostrarAsientosReservados()">
            Confirma tu Reserva
        </button>
        <script>
            function mostrarAsientosReservados() {
                var asientosReservados = document.getElementsByClassName("reservado");

                if (asientosReservados.length > 0) {
                    var asientosReservadosString = "Asientos Reservados: ";
                    for (var i = 0; i < asientosReservados.length; i++) {
                        asientosReservadosString += asientosReservados[i].id + " ";
                    }
                    alert(asientosReservadosString);
                } else {
                    alert("No hay asientos reservados.");
                }
            }
        </script>
        <script>
            $(document).ready(function() {
                $('#confirmarReserva').click(function() {
                    $.ajax({
                        url: 'ServletConfirmarReserva',
                        type: 'POST',
                        data: { },
                        success: function(response) {
                            window.location.href = 'PaginaReservas.jsp';
                        },
                        error: function() {
                            alert('Error al procesar la solicitud');
                        }
                    });
                });
            });
        </script>
    </body>
</html>
</html>