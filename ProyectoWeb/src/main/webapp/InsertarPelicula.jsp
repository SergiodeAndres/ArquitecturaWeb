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
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            max-width: 100%;
            text-align: center;
            padding: 20px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
        }
        
        form{
            overflow: scroll;
            width: 600px;
            height: 400px;
            margin-right: 24%;
            margin-left: 24%;
        }

        button {
            background-color: #4caf50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
     <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
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
    
    <div class="container">
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
    </div>
</body>
</html>
