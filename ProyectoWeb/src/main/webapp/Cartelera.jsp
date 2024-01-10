<%-- 
    Document   : Cartelera
    Created on : 1 ene 2024, 18:37:08
    Author     : paser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap" %>
<%@page import="Utilitis.Pelicula" %>
<%@page import="Utilitis.ModeloDatos" %>
<% ModeloDatos modeloDatos = new ModeloDatos();
modeloDatos.abrirConexion();%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="estilos.css">
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    </head>
    <body>

        <%String username = (String) session.getAttribute("username");%>

        <div class="contenedor_cabecera">
            <header>EsCineElCine</header>

            <%if (username == null){%>

            <nav class="menu">
                <ul>
                    <li><a class="option" href="index.html">
                            <p>Inicio</p>
                        </a></li>
                    <li><a class="option" href="IniciarSesion.html">
                            <p>Iniciar sesión</p>
                        </a></li>
                    <li><a class="option" href="Registrarse.html">
                            <p>Registrarse</p>
                        </a></li>
                </ul>
            </nav>

            <%}else{%>

            <h1>Usuario: <%=(String) session.getAttribute("username")%></h1>

            <nav class="menu">
                <ul>
                    <li><a class="option" href="MainUsuario.jsp">
                            <p>Inicio</p>
                        </a></li>
                    <li><a class="option" href="cerrarSesion.jsp">
                            <p>Cerrar Sesión</p>
                        </a></li>
                </ul>
            </nav>

            <%}%>

        </div>

        <%
            HashMap<String, Pelicula> peliculas = (HashMap) modeloDatos.getPeliculas();
            session.setAttribute("peliculas", peliculas);
            modeloDatos.cerrarConexion();
        %>

        <div class="contenedor cartelera">
            <div class="titulo1">Cartelera</div>
            <div class="contenedor_espacio">
                <%for(String clave:peliculas.keySet()){%>
                <%Pelicula p = (Pelicula) peliculas.get(clave);%>
                <div class="pelicula">
                    <img src="<%=p.getImagen()%>"/>
                    <div class="contenido">
                        <div class="elementos">
                            <button class="peliculabutton" id="<%=p.getNombre().replaceAll("\\s", "")%>">Ver película</button>
                            <script>
                                $(document).ready(function () {
                                    $('#<%=p.getNombre().replaceAll("\\s", "")%>').click(function () {
                                        $.ajax({
                                            url: 'CargarInformacionPelicula',
                                            type: 'POST',
                                            data: {peliculaSeleccionada: '<%=p.getNombre()%>'},
                                            success: function (response) {
                                                window.location.href = 'InformacionPelicula.jsp';
                                            },
                                            error: function () {
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