<%-- 
    Document   : InformacionPelicula
    Created on : 3 ene 2024, 9:43:52
    Author     : paser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="Utilitis.Pelicula" %>
<%@page import="Utilitis.Sesion" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="estilos.css">
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    </head>
    <body>
        <header>EsCineElCine</header>
        <nav class="menu">
            <ul>
              <li><a class="option" href="Cartelera.jsp">
                <p>Volver</p>
              </a></li>
            </ul>
        </nav>
        
        <%Pelicula peliculaSeleccionada = (Pelicula) session.getAttribute("datos_pelicula_seleccionada");%>
        
        <div class="contenedor informacionPelicula">
            <div class="contenedorH">
                <div class="contenedorimagen">
                    <img src="<%=peliculaSeleccionada.getImagen()%>">
                </div>
            </div>
            <div class="contenedorH">
                <div class="informacion">
                    <p>Título: <%=peliculaSeleccionada.getNombre()%>.</p>
                    <p>Sinopsis: <%=peliculaSeleccionada.getSinopsis()%>.</p> 
                    <a href="<%=peliculaSeleccionada.getPaginaoficial()%>">Página oficial.</a>
                    <p>Título original: <%=peliculaSeleccionada.getTitulooriginal()%>.</p>
                    <p>Género: <%=peliculaSeleccionada.getGenero()%>.</p>
                    <p>Nacionalidad: <%=peliculaSeleccionada.getNacionalidad()%>.</p>
                    <p>Duración: <%=peliculaSeleccionada.getDuracion()%> min.</p>
                    <p>Año: <%=peliculaSeleccionada.getAno()%>.</p>
                    <p>Distribuidora: <%=peliculaSeleccionada.getDistribuidora()%>.</p>
                    <p>Director: <%=peliculaSeleccionada.getDirector()%>.</p>
                    <p>Clasificación: <%=peliculaSeleccionada.getClasificacion()%>.</p>
                    <p>Actores: <%=peliculaSeleccionada.mostrarActores()%>.</p>
                </div>
            </div>
        </div>
        
        <%String username = (String) session.getAttribute("username");%>
                
        <%if (!(username == null)){%>
                
            <%ArrayList<Sesion> sesiones = (ArrayList) session.getAttribute("sesiones_pelicula_seleccionada");%>

            <div class="contenedor informacionPelicula">
                <div class="contenedorV">
                    <div class="titulo2">Sesiones</div>
                    <div class="contenedor_espacio_hora">
                        <%for (Sesion sesion:sesiones){%>
                            <div class="contenedor_hora">
                                
                                <button class="horariobutton" id="<%=sesion.getNombrePelicula().replaceAll("\\s", "")%><%=sesion.getNombreSala().replaceAll("\\s", "")%><%=sesion.getHora().toString().replaceAll(":", "")%><%=sesion.getFecha().toString().replaceAll("-", "")%>">
                                    <%=sesion.getHora()%> <%=sesion.getFecha()%>
                                </button>
                                <script>
                                $(document).ready(function() {
                                    $('#<%=sesion.getNombrePelicula().replaceAll("\\s", "")%><%=sesion.getNombreSala().replaceAll("\\s", "")%><%=sesion.getHora().toString().replaceAll(":", "")%><%=sesion.getFecha().toString().replaceAll("-", "")%>').click(function() {
                                        $.ajax({
                                            url: 'ServletReserva',
                                            type: 'POST',
                                            data: { nombrePelicula: '<%=sesion.getNombrePelicula()%>', nombreSala: '<%=sesion.getNombreSala()%>', fechaSesion: '<%=sesion.getFecha()%>', horaSesion: '<%=sesion.getHora()%>'},
                                            success: function(response) {
                                                window.location.href = 'PaginaReservas.jsp';
                                            },
                                            error: function() {
                                                alert('Error al procesar la solicitud');
                                            }
                                        });
                                    });
                                });
                                </script>
                            </div>
                        <%}%>
                    </div>
                </div>
            </div>

            <script>
                // Función para redirigir al hacer clic en el botón de reserva
                function generarServletReserva() {
                    ServletReserva();
                }
            </script>
            
            <div class="contenedor informacionPelicula">
                <div class="contenedorV">
                    <div class="titulo2">Comentarios:</div>
                    <div class="titulo3">Escribe tu comentario:</div>
                    <form id="formulario" action="InsertarComentario" method="post">
                        <textarea name="comentario" rows="10" cols="80" required></textarea><br>
                        <input type="submit" class="formulariobutton" value="Enviar comentario">
                    </form>
                </div>
            </div>
            

                    
            <%ArrayList<String> comentarios = (ArrayList) session.getAttribute("comentarios_pelicula_seleccionada");%>

            <div class="contenedor informacionPelicula">
                <div class="contenedorV">
                    <div class="titulo3">Comentarios de otros usuarios:</div>
                    <%for (String comentario:comentarios){%>
                        <div class="comentario_pelicula">
                            <p><%=comentario%>.</p>
                        </div>
                    <%}%>
                </div>
            </div>
            
        <%}%>
    </body>
</html>

