<%-- 
    Document   : Cartelera
    Created on : 1 ene 2024, 18:37:08
    Author     : paser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap" %>
<%@page import="Utilitis.Pelicula" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <header>EsCineElCine</header>
        <nav class="menu">
            <ul>
              <li><a class="option" href="#">
                <p>Inicio</p>
              </a></li>
              <li><a class="option" href="#">
                <p>Iniciar sesión</p>
              </a></li>
              <li><a class="option" href="#">
                <p>Registrarse</p>
              </a></li>
            </ul>
        </nav>
        
        <%HashMap<String, Pelicula> peliculas = (HashMap) session.getAttribute("peliculas");%>
        
        <div class="contenedor">
            <div class="contenedor_titulo">Cartelera</div>
            <div class="contenedor_espacio">
                <%for(String clave:peliculas.keySet()){%>
                    <%Pelicula p = (Pelicula) peliculas.get(clave);%>
                    <div class="pelicula">
                        <img src="<%=p.getImagen()%>"/>
                        <div class="contenido">
                            <div class="elementos">
                                <input type="button" class="peliculabutton" value="Ver película"/>
                            </div>
                        </div>
                    </div>
                <%}%>
            </div>
        </div>
    </body>
</html>
