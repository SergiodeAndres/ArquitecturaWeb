<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Utilitis.Sala" %>
<%@page import="Utilitis.ModeloDatos" %>
<%@page import="java.util.ArrayList" %>
<% ModeloDatos modeloDatos = new ModeloDatos();%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de Salas</title>
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    </head>
    <body>
        <table>
            <!-- Encabezados -->
            <tr>
                <th>Nombre</th>
                <th>Filas</th>
                <th>Columnas</th>
            </tr>
            <!<!-- Filas -->
        <% 
            ArrayList<Sala> salas = modeloDatos.getSalas();
            
            for (Sala s : salas) {
        %>
            <tr>
                <td><%= s.getNombre() %></td>
                <td><%= s.getFilas() %></td>
                <td><%= s.getColumnas() %></td>
            </tr>
        <%
            }
        %>
        </table>
        <h2>Crear Sala</h2>
        <form id="formulario" action="SalasServlet" method="post">
            <label>Nombre: <input type="text" name="nombre" maxlength = "255" required></label><br>
            <label>Número de filas: <input type="number" name="filas" min="1" value="1" required></label><br>
            <label>Número de columnas: <input type="number" name="columnas" min="1" value="1" required></label><br>
            <input type="submit" value="Crear">
        </form>
        <h2> Borrar Sala </h2> 
        <% 
            for (Sala s : salas) {
        %>
            <button id="BotonBorrar<%=s.getNombre().replaceAll("\\s", "") %>"> <%=s.getNombre()%> </button>
            <script>
            $(document).ready(function() {
                $('#BotonBorrar<%=s.getNombre().replaceAll("\\s", "")%>').click(function() {
                    $.ajax({
                        url: 'SalasServlet',
                        type: 'POST',
                        data: { modo: 'eliminar', nombreSala: '<%=s.getNombre() %>',
                        filasSala: '<%=s.getFilas() %>', columnasSala: '<%=s.getColumnas() %>'},
                        success: function(response) {
                            window.location.href = 'AdminSalas.jsp';
                        },
                        error: function() {
                            alert('Error al procesar la solicitud');
                        }
                    });
                });
            });
        </script>
        <%
            }
        %>
        <script>
        $(document).ready(function() {
            $('#formulario').submit(function(event) {
                event.preventDefault(); // Evitar el envío del formulario por defecto
                var formData = $(this).serialize() + '&modo=añadir';
                $.ajax({
                    url: 'SalasServlet',
                    type: 'POST',
                    data: formData, // Obtener los datos del formulario
                    success: function(response) {
                        if (response.trim() !== "") {
                            alert(response); 
                        } else {
                            window.location.href = 'AdminSalas.jsp';
                        }
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
