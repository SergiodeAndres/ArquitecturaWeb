<%-- 
    Document   : AdminCartelera
    Created on : 6 ene 2024, 12:44:00
    Author     : paser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap" %>
<%@page import="Utilitis.Pelicula" %>
<%@page import="Utilitis.ModeloDatos" %>
<% ModeloDatos modeloDatos = new ModeloDatos();
modeloDatos.abrirConexion();%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <header>EsCineElCine</header>
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
        %>
        
        <%HashMap<String, Pelicula> peliculas = (HashMap) modeloDatos.getPeliculas();
            session.setAttribute("peliculas", peliculas);
        %>
        
        <div class="contenedor cartelera">
            <div class="titulo1">Cartelera</div>
            <a class="option" href="InsertarPelicula.jsp">
                <p>Añadir</p>
            </a>
            <div class="contenedor_espacio">
                <%for(String clave:peliculas.keySet()){%>
                    <%Pelicula p = (Pelicula) peliculas.get(clave);%>
                    <div class="pelicula">
                        <img src="<%=p.getImagen()%>"/>
                        <div class="contenido">
                            <div class="elementos">
                                <button class="peliculabutton" id="Modificar<%=p.getNombre().replaceAll("\\s", "")%>">Modificar</button>
                                <script>
                                $(document).ready(function() {
                                    $('#Modificar<%=p.getNombre().replaceAll("\\s", "")%>').click(function() {
                                        $.ajax({
                                            url: 'CargarInformacionPelicula',
                                            type: 'POST',
                                            data: { peliculaSeleccionada: '<%=p.getNombre()%>'},
                                            success: function(response) {
                                                if (response.trim() !== "")
                                                {
                                                    alert(response);
                                                }
                                                else
                                                {
                                                    window.location.href = 'ModificarPelicula.jsp';
                                                }
                                            },
                                            error: function() {
                                                alert('Error al procesar la solicitud');
                                            }
                                        });
                                    });
                                });
                                </script>
                                <button class="peliculabutton" id="Borrar<%=p.getNombre().replaceAll("\\s", "")%>">Borrar</button>
                                <script>
                                $(document).ready(function() {
                                    $('#Borrar<%=p.getNombre().replaceAll("\\s", "")%>').click(function() {
                                        $.ajax({
                                            url: 'EliminarPelicula',
                                            type: 'POST',
                                            data: { peliculaSeleccionada: '<%=p.getNombre()%>', imagen: '<%=p.getImagen()%>'},
                                            success: function(response) {
                                                if (response.trim() !== "")
                                                {
                                                    alert(response);
                                                }
                                                else
                                                {
                                                    window.location.href = 'AdminCartelera.jsp';
                                                }
                                            },
                                            error: function() {
                                                alert('Error al procesar la solicitud');
                                            }
                                        });
                                    });
                                });
                                </script>
                            </div>
                        </div>
                    </div>
                <%}%>
            </div>
        </div>
    </body>
</html>