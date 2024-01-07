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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
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
        <%
            Sesion sesion = (Sesion) session.getAttribute("sesionActual");
            Sala sala = (Sala) session.getAttribute("salaActualEntrada");
            int filas = sala.getFilas();
            int columnas = sala.getColumnas();
            HashMap<String, Pelicula> peliculas = modeloDatos.getPeliculas(); 
            ArrayList<Sala> salas = modeloDatos.getSalas();
         %>
         <h1>Sesión Actual</h1>
        <%= sesion.getFecha().toString() %>
        <%= sesion.getHora().toString() %>
        <%= sesion.getNombreSala() %>
        <%= sesion.getNombrePelicula() %>
        <h2>Elegir Pelicula: </h2> 
        <select id="seleccionPelicula">
        <% 
            for(String clave:peliculas.keySet()) {
                Pelicula p = (Pelicula) peliculas.get(clave);
        %>
            <option value="<%=p.getNombre()%>"> <%=p.getNombre()%> </option>
        <%
            }
        %>
        </select>
        <h2> Elegir Sala </h2> 
        <select id="seleccionSala">
        <% 
            for (Sala s : salas) {
        %>
            <option value="<%=s.getNombre()%>"> <%=s.getNombre()%> </option>
        <%
            }
        %>
        </select>
        <script>
        var variablesalaActual = '<%= sala.getNombre() %>';
        if (variablesalaActual !== null) {
            var selectElement = document.getElementById('seleccionSala');
            for (var i = 0; i < selectElement.options.length; i++) {
                if (selectElement.options[i].value === variablesalaActual) {
                    selectElement.options[i].selected = true;
                    break;
                }
            }
        }
        </script>
        <h2> Elegir Fila </h2>
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
        <h2>Elegir Fecha: </h2>
        <form id="formulario" action="SesionesServlet" method="post">
            <input type="datetime-local" id= "nuevaSesion" name="fechaHora" required><br>
            <input type="submit" value="Añadir sesión">
        </form>
        
        <script>
        var fechaHoraActual = new Date();
        fechaHoraActual.setTime(fechaHoraActual.getTime() + (1 * 60 * 60 * 1000));
        var fechaHoraModificada = fechaHoraActual.toISOString().slice(0, -8);
        document.getElementById('nuevaSesion').min = fechaHoraModificada;
        $(document).ready(function() {
            $('#formulario').submit(function(event) {
                event.preventDefault();
                var formData = $(this).serialize() + '&modo=editarEntrada&pelicula='+ $("#seleccionPelicula").val() + '&sala='+ $("#seleccionSala").val() + 
                        '&fila=' + $("#seleccionFila").val() + '&columna=' + $("#seleccionColumna").val();
                $.ajax({
                    url: 'SesionesServlet',
                    type: 'POST',
                    data: formData,
                    success: function(response) {
                        if (response.trim() !== "") {
                            alert(response); 
                            } else {
                                var formData = 'modo=buscar&pelicula='+ $("#seleccionPelicula").val() + '&sala='+ $("#seleccionSala").val();
                                    $.ajax({
                                        url: 'SesionesServlet',
                                        type: 'POST',
                                        data: formData,
                                        success: function(response) {
                                            window.location.href = 'AdminEntradas.jsp';
                                        },
                                        error: function() {
                                            alert('Error al procesar la solicitud');
                                        }
                                    });
                                }
                        },
                    error: function() {
                        alert('Error al procesar la solicitud');
                    }
                });
            });
        });
        </script>
        <script>
            var salaSeleccionada = $("#seleccionSala").val();
        </script>
        <script>
            $(document).ready(function() {
                $('#seleccionSala').change(function() {
                    salaSeleccionada = $(this).val();
                    var formData = 'modo=actualizarSala&sala='+ $("#seleccionSala").val();
                    $.ajax({
                        url: 'SesionesServlet',
                        type: 'POST',
                        data: formData,
                        success: function(response) {
                            window.location.href = 'EditarEntrada.jsp';
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
