<%-- 
    Document   : InsertarPelicula
    Created on : 8 ene 2024, 15:55:02
    Author     : sergi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="estilos.css">
        <title>Crear Película</title>
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
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
        <div class="contenedor_cuerpo1">
            <div class="contenedor_formulario2">
                <h2>Datos de la película</h2>
                <form id="FormularioPelicula" action="InsertarPelicula" method="post" enctype="multipart/form-data" >
                    <label for="nombrePelicula">Nombre de la película:</label>
                    <input type="text" id="nombrePelicula" name="nombrePelicula" required>
                    <label for="sinopsisPelicula">Sinopsis de la película:</label>
                    <textarea id="sinopsisPelicula" name="sinopsisPelicula" rows="5" cols="75" required></textarea>
                    <label for="paginaOficialPelicula">Enlace a la página oficial:</label>
                    <input type="text" id="paginaOficialPelicula" name="paginaOficialPelicula" required>
                    <label for="tituloOriginalPelicula">Título original de la película:</label>
                    <input type="text" id="tituloOriginalPelicula" name="tituloOriginalPelicula" required>
                    <label for="generoPelicula">Género de la película:</label>
                    <input type="text" id="generoPelicula" name="generoPelicula" required>
                    <label for="nacionalidadPelicula">Nacionalidad de la película:</label>
                    <input type="text" id="nacionalidadPelicula" name="nacionalidadPelicula" required>
                    <label for="duracionPelicula">Duración de la película:</label>
                    <input type="number" id="duracionPelicula" name="duracionPelicula" required>
                    <label for="fechaPelicula">Año en en el que se estrenó la película:</label>
                    <input type="number" id="fechaPelicula" name="fechaPelicula" required>
                    <label for="distribuidoraPelicula">Distribuidora de la película:</label>
                    <input type="text" id="distribuidoraPelicula" name="distribuidoraPelicula" required>
                    <label for="directorPelicula">Director de la película:</label>
                    <input type="text" id="directorPelicula" name="directorPelicula" required>
                    <label for="clasificacionPelicula">Clasificación de la película:</label>
                    <input type="text" id="clasificacionPelicula" name="clasificacionPelicula" required>
                    <label for="portadaPelicula">Portada de la película:</label>
                    <input type="file" id="portadaPelicula" name="portadaPelicula" required>
                    <label for="actoresPelicula">Actores de la película:</label>
                    <textarea id="actoresPelicula" name="actoresPelicula" rows="5" cols="75" required></textarea>

                    <button type="submit">Añadir película</button>
                </form>
                <script>
                        $(document).ready(function () {
                            $('#FormularioPelicula').submit(function (event) {
                                event.preventDefault();
                                var formData = new FormData($('#FormularioPelicula')[0]);
                                $.ajax({
                                    url: 'InsertarPelicula',
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
    </body>
</html>
