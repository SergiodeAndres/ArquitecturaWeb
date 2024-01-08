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
            Sesion sesion = (Sesion) session.getAttribute("sesionActual");
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
        <h2>Elegir fecha</h2>
        <form id="formularioEdicion" action ="SesionesServlet" method="post">
            <input type="datetime-local" id= "nuevaSesion" name="fechaHora" required><br>
            <input type="submit" value="Editar sesión">
        </form>
        
        <script>
        var fechaHoraActual = new Date();
        fechaHoraActual.setTime(fechaHoraActual.getTime() + (1 * 60 * 60 * 1000));
        var fechaHoraModificada = fechaHoraActual.toISOString().slice(0, -8);
        document.getElementById('nuevaSesion').min = fechaHoraModificada;
        </script>
        <script>
        $(document).ready(function() {
            $('#formularioEdicion').submit(function(event) {
                event.preventDefault();
                var formData = $(this).serialize() + '&modo=editarSesion&pelicula='+ $("#seleccionPelicula").val() + '&sala='+ $("#seleccionSala").val();
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
    </body>
</html>

