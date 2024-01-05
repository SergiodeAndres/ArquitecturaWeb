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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de Sesiones</title>
        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    </head>
    <body>
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
        String salaActual =(String) session.getAttribute("salaActual");
        String peliculaActual =(String) session.getAttribute("peliculaActual");
        HashMap<String, Pelicula> peliculas = modeloDatos.getPeliculas(); 
        ArrayList<Sala> salas = modeloDatos.getSalas();
        %>
        
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
        <script>
        var variablepeliculaActual = '<%= session.getAttribute("peliculaActual") %>';
        if (variablepeliculaActual !== null) {
            var selectElement = document.getElementById('seleccionPelicula');
            for (var i = 0; i < selectElement.options.length; i++) {
                if (selectElement.options[i].value === variablepeliculaActual) {
                    selectElement.options[i].selected = true;
                    break;
                }
            }
        }
        </script>
        
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
        <script>
        var variablesalaActual = '<%= session.getAttribute("salaActual") %>';
        if (variablesalaActual !== null) {
            var selectElement = document.getElementById('seleccionSala');
            for (var i = 0; i < selectElement.options.length; i++) {
                if (selectElement.options[i].value === variablesalaActual) {
                    selectElement.options[i].selected = true;
                    break;
                }
            }
        }
        </script>
        
        <h2 id="enunciadoSesiones">Sesiones Actuales</h2>
        <% 
            ArrayList<Sesion> sesiones = (ArrayList<Sesion>) session.getAttribute("sesionesPeliculaActualSalaActual");
            if (sesiones != null && !sesiones.isEmpty())
            {
                for (Sesion s: sesiones)
                { %>
                    <button id="Boton<%=s.getHora().toString().replaceAll(":", "")%>"><%= s.getFecha().toString() %> <%= s.getHora().toString() %></button>
                    <script>
                    $(document).ready(function() {
                        $('#Boton<%=s.getHora().toString().replaceAll(":", "")%>').click(function() {
                            $.ajax({
                                url: 'SesionesServlet',
                                type: 'POST',
                                data: { modo: 'buscarEntradas', nombrePelicula: '<%=s.getNombrePelicula() %>',
                                nombreSala: '<%=s.getNombreSala() %>', sesionHora: '<%=s.getHora().toString() %>',
                                sesionFecha: '<%=s.getFecha().toString() %>'},
                                success: function(response) {
                                    window.location.href = 'AdminEntradas.jsp';
                                },
                                error: function() {
                                    alert('Error al procesar la solicitud');
                                }
                            });
                        });
                    });
                </script>
                <%}
            }
            else if (sesiones != null && sesiones.isEmpty())
            { %>
                No hay sesiones en esta sala
            <%}
        %>
        
        <p id="enunciadoEntradas"></p>
        <% 
            ArrayList<Entrada> entradas = (ArrayList<Entrada>) session.getAttribute("entradaSesionActual");
            if (entradas != null && !entradas.isEmpty())
            {
                for (Entrada e: entradas)
                { %>
                    <button id="Boton">F<%= e.getFila()%>C<%= e.getColumna()%></button>
                <%}
            }
        %>
        
        <script>
            var peliculaSeleccionada = $("#seleccionPelicula").val();
            var salaSeleccionada = $("#seleccionSala").val();
            $('#enunciadoSesiones').text('Sesiones de ' + peliculaSeleccionada + ' en ' + salaSeleccionada);
            $(document).ready(function() {
                $('#seleccionSala').change(function() {
                    salaSeleccionada = $(this).val();
                    $('#enunciadoSesiones').text('Sesiones de ' + peliculaSeleccionada + ' en ' + salaSeleccionada);
                    var formData = '&modo=buscar&pelicula='+ $("#seleccionPelicula").val() + '&sala='+ $("#seleccionSala").val();
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
                });
            });
            $(document).ready(function() {
                $('#seleccionPelicula').change(function() {
                    peliculaSeleccionada = $(this).val();
                    $('#enunciadoSesiones').text('Sesiones de ' + peliculaSeleccionada + ' en ' + salaSeleccionada);
                    var formData = '&modo=buscar&pelicula='+ $("#seleccionPelicula").val() + '&sala='+ $("#seleccionSala").val();
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
                });
            });
        </script>
        
        <h2>Añadir sesión en sala actual</h2>
        <form id="formulario" action="SalasServlet" method="post">
            <input type="datetime-local" id= "nuevaSesion" name="fechaHora" required><br>
            <input type="submit" value="Añadir sesión">
        </form>
        
        <script>
        var fechaHoraActual = new Date();
        fechaHoraActual.setTime(fechaHoraActual.getTime() + (1 * 60 * 60 * 1000));
        var fechaHoraModificada = fechaHoraActual.toISOString().slice(0, -8);
        document.getElementById('nuevaSesion').min = fechaHoraModificada;
        
        $(document).ready(function() {
            $('#formulario').submit(function(event) {
                event.preventDefault();
                var formData = $(this).serialize() + '&modo=añadir&pelicula='+ $("#seleccionPelicula").val() + '&sala='+ $("#seleccionSala").val();
                $.ajax({
                    url: 'SesionesServlet',
                    type: 'POST',
                    data: formData,
                    success: function(response) {
                        if (response.trim() !== "") {
                            alert(response); 
                            } else {
                                var formData = '&modo=buscar&pelicula='+ $("#seleccionPelicula").val() + '&sala='+ $("#seleccionSala").val();
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
