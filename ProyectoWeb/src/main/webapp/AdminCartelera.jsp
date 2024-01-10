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
        
        <%
            HashMap<String, Pelicula> peliculas = (HashMap) modeloDatos.getPeliculas();
            session.setAttribute("peliculas", peliculas);
            modeloDatos.cerrarConexion();
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