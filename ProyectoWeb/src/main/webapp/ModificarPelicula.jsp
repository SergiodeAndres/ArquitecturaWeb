<%-- 
    Document   : ModificarPelicula
    Created on : 7 ene 2024, 10:30:58
    Author     : paser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="Utilitis.Pelicula" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <%
            String username = (String) session.getAttribute("username");
            if(username == null || !username.equals("admin"))
            {
                response.sendRedirect("error.jsp");
            }
            else
            {
        %>
        <div class="contenedor_cabecera">
            <header>EsCineElCine</header>
            <nav class="menu">
                <ul>
                    <li><a class="option" href="AdminSalas.jsp">
                            <p>Salas</p>
                        </a></li>
                    <li><a class="option" href="AdminCartelera.jsp">
                            <p>Películas</p>
                        </a></li>
                    <li><a class="option" href="VerReservas.jsp">
                            <p>Reservas</p>
                        </a></li>
                    <li><a class="option" href="AdminEntradas.jsp">
                            <p>Entradas y sesiones</p>
                        </a></li>
                    <li><a class="option" href="Informes.jsp">
                            <p>Informes</p>
                        </a></li>
                    <li><a class="option" href="cerrarSesion.jsp">
                            <p>Cerrar Sesión</p>
                        </a></li>
                </ul>
            </nav>
        </div>


        <%Pelicula peliculaSeleccionada = (Pelicula) session.getAttribute("datos_pelicula_seleccionada");%>
        <%
            ArrayList<String> actores = peliculaSeleccionada.getActores();


            String cadena = "";

            for (String nombre:actores){
                cadena += nombre + ", ";
            }
        %>
        <div class="contenedor informacionPelicula">
            <div class="contenedorH">
                <div class="contenedorimagen">
                    <p>Portada:</p>
                    <img src="<%=peliculaSeleccionada.getImagen()%>">
                </div>
            </div>
            <div class="contenedorH">
                <div class="informacion">
                    <h2>Datos de la película</h2>
                    <form id="FormularioPelicula" action="ModificarPelicula" method="post" enctype="multipart/form-data" >
                        <label for="nombrePelicula">Nombre de la película:</label>
                        <p><%=peliculaSeleccionada.getNombre()%>.</p>
                        <label for="sinopsisPelicula">Sinopsis de la película:</label><br>
                        <textarea id="sinopsisPelicula" name="sinopsisPelicula" rows="5" cols="40" required><%=peliculaSeleccionada.getSinopsis()%></textarea><br>

                        <label for="paginaOficialPelicula">Enlace a la página oficial:</label><br>
                        <input type="text" id="paginaOficialPelicula" name="paginaOficialPelicula" value="<%=peliculaSeleccionada.getPaginaoficial()%>" required><br>

                        <label for="tituloOriginalPelicula">Título original de la película:</label><br>
                        <input type="text" id="tituloOriginalPelicula" name="tituloOriginalPelicula" value="<%=peliculaSeleccionada.getTitulooriginal()%>" required><br>

                        <label for="generoPelicula">Género de la película:</label><br>
                        <input type="text" id="generoPelicula" name="generoPelicula" value="<%=peliculaSeleccionada.getGenero()%>" required><br>

                        <label for="nacionalidadPelicula">Nacionalidad de la película:</label><br>
                        <input type="text" id="nacionalidadPelicula" name="nacionalidadPelicula" value="<%=peliculaSeleccionada.getNacionalidad()%>" required><br>

                        <label for="duracionPelicula">Duración de la película:</label><br>
                        <input type="number" id="duracionPelicula" name="duracionPelicula" value="<%=peliculaSeleccionada.getDuracion()%>" required> min<br>

                        <label for="fechaPelicula">Año de estreno de la película:</label><br>
                        <input type="number" id="fechaPelicula" name="fechaPelicula" value="<%=peliculaSeleccionada.getAno()%>" required><br>

                        <label for="distribuidoraPelicula">Distribuidora de la película:</label><br>
                        <input type="text" id="distribuidoraPelicula" name="distribuidoraPelicula" value="<%=peliculaSeleccionada.getDistribuidora()%>" required><br>

                        <label for="directorPelicula">Director de la película:</label><br>
                        <input type="text" id="directorPelicula" name="directorPelicula" value="<%=peliculaSeleccionada.getDirector()%>" required><br>

                        <label for="clasificacionPelicula">Clasificación de la película:</label><br>
                        <input type="text" id="clasificacionPelicula" name="clasificacionPelicula" value="<%=peliculaSeleccionada.getClasificacion()%>" required><br>

                        <label for="portadaPelicula">Nueva portada de la película:</label><br>

                        <input type="file" id="portadaPelicula" name="portadaPelicula" required><br>
                        <label for="actoresPelicula">Actores de la película:</label><br>
                        <textarea id="actoresPelicula" name="actoresPelicula" rows="5" cols="40" required><%=peliculaSeleccionada.mostrarActores()%></textarea><br>

                        <button type="submit">Modificar película</button>
                    </form>
                    <script>
                        $(document).ready(function () {
                            $('#FormularioPelicula').submit(function (event) {
                                event.preventDefault();
                                var formData = new FormData($('#FormularioPelicula')[0]);
                                $.ajax({
                                    url: 'ModificarPelicula',
                                    type: 'POST',
                                    data: formData,
                                    processData: false,
                                    contentType: false,
                                    success: function (response) {
                                        if (response.trim() !== "")
                                        {
                                            alert(response);
                                        } else
                                        {
                                            window.location.href = 'AdminCartelera.jsp';
                                        }
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
    </body>
</html>
