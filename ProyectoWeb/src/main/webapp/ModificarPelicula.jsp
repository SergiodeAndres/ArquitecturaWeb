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
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <header>EsCineElCine</header>
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
                        <input type="text" id="nombrePelicula" name="nombrePelicula" value="<%=peliculaSeleccionada.getNombre()%>" required>
                        <p>Título: <%=peliculaSeleccionada.getNombre()%>.</p>
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
                </div>
            </div>
        </div>
    </body>
</html>
