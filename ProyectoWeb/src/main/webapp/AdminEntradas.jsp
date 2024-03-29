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
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de Sesiones</title>
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
                            <p>Rntradas y sesiones</p>
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
        String salaActual =(String) session.getAttribute("salaActual");
        String peliculaActual =(String) session.getAttribute("peliculaActual");
        HashMap<String, Pelicula> peliculas = modeloDatos.getPeliculas(); 
        ArrayList<Sala> salas = modeloDatos.getSalas();
        %>

        <div class="contenedor_cuerpo2">
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
            <h2>Añadir sesión en sala actual</h2>
            <form id="formulario" action="SesionesServlet" method="post">
                <input type="datetime-local" id= "nuevaSesion" name="fechaHora" required><br>
                <input type="submit" value="Añadir sesión">
            </form>

            <script>
                var fechaHoraActual = new Date();
                fechaHoraActual.setTime(fechaHoraActual.getTime() + (1 * 60 * 60 * 1000));
                var fechaHoraModificada = fechaHoraActual.toISOString().slice(0, -8);
                document.getElementById('nuevaSesion').min = fechaHoraModificada;

                $(document).ready(function () {
                    $('#formulario').submit(function (event) {
                        event.preventDefault();
                        var formData = $(this).serialize() + '&modo=añadir&pelicula=' + $("#seleccionPelicula").val() + '&sala=' + $("#seleccionSala").val();
                        $.ajax({
                            url: 'SesionesServlet',
                            type: 'POST',
                            data: formData,
                            success: function (response) {
                                if (response.trim() !== "") {
                                    alert(response);
                                } else {
                                    var formData = 'modo=buscar&pelicula=' + $("#seleccionPelicula").val() + '&sala=' + $("#seleccionSala").val();
                                    $.ajax({
                                        url: 'SesionesServlet',
                                        type: 'POST',
                                        data: formData,
                                        success: function (response) {
                                            window.location.href = 'AdminEntradas.jsp';
                                        },
                                        error: function () {
                                            alert('Error al procesar la solicitud');
                                        }
                                    });
                                }
                            },
                            error: function () {
                                alert('Error al procesar la solicitud');
                            }
                        });
                    });
                });
            </script>
            <h2 id="enunciadoSesiones">Sesiones Actuales</h2>
            <% 
                ArrayList<Sesion> sesiones = (ArrayList<Sesion>) session.getAttribute("sesionesPeliculaActualSalaActual");
                if (sesiones != null && !sesiones.isEmpty())
                {
                    for (Sesion s: sesiones)
                    { %>
            <%= s.getFecha().toString() %> <%= s.getHora().toString() %> 
            <button id="BotonEntradas<%=s.getHora().toString().replaceAll(":", "")%>">Ver entradas</button>
            <button id="BotonEliminar<%=s.getHora().toString().replaceAll(":", "")%>">Eliminar Sesión</button>
            <button id="BotonEditar<%=s.getHora().toString().replaceAll(":", "")%>">Editar Sesión</button>
            <button id="BotonAñadirEntradas<%=s.getHora().toString().replaceAll(":", "")%>">Añadir Entrada</button>
            <br>
            <script>
                $(document).ready(function () {
                    $('#BotonEntradas<%=s.getHora().toString().replaceAll(":", "")%>').click(function () {
                        $.ajax({
                            url: 'SesionesServlet',
                            type: 'POST',
                            data: {modo: 'buscarEntradas', nombrePelicula: '<%=s.getNombrePelicula() %>',
                                        nombreSala: '<%=s.getNombreSala() %>', sesionHora: '<%=s.getHora().toString() %>',
                                        sesionFecha: '<%=s.getFecha().toString() %>'},
                            success: function (response) {
                                window.location.href = 'AdminEntradas.jsp';
                            },
                            error: function () {
                                alert('Error al procesar la solicitud');
                            }
                        });
                    });
                });
                $(document).ready(function () {
                    $('#BotonEliminar<%=s.getHora().toString().replaceAll(":", "")%>').click(function () {
                        var respuesta = confirm("Se va a realizar la siguiente acción: \n Borrar Sesión seleccionada. \n ¿Desea continuar?");
                        if (respuesta === true)
                        {
                            $.ajax({
                                url: 'SesionesServlet',
                                type: 'POST',
                                data: {modo: 'eliminarSesion', nombrePelicula: '<%=s.getNombrePelicula() %>',
                                            nombreSala: '<%=s.getNombreSala() %>', sesionHora: '<%=s.getHora().toString() %>',
                                            sesionFecha: '<%=s.getFecha().toString() %>'},
                                success: function (response) {
                                    if (response.trim() !== "")
                                    {
                                        alert(response);
                                    } else
                                    {
                                        var formData = 'modo=buscar&pelicula=' + $("#seleccionPelicula").val() + '&sala=' + $("#seleccionSala").val();
                                        $.ajax({
                                            url: 'SesionesServlet',
                                            type: 'POST',
                                            data: formData,
                                            success: function (response) {
                                                window.location.href = 'AdminEntradas.jsp';
                                            },
                                            error: function () {
                                                alert('Error al procesar la solicitud');
                                            }
                                        });
                                    }
                                },
                                error: function () {
                                    alert('Error al procesar la solicitud');
                                }
                            });
                        }
                    });
                });
                $(document).ready(function () {
                    $('#BotonEditar<%=s.getHora().toString().replaceAll(":", "")%>').click(function () {
                        $.ajax({
                            url: 'SesionesServlet',
                            type: 'POST',
                            data: {modo: 'redirigirEditarSesion', nombrePelicula: '<%=s.getNombrePelicula() %>',
                                        nombreSala: '<%=s.getNombreSala() %>', sesionHora: '<%=s.getHora().toString() %>',
                                        sesionFecha: '<%=s.getFecha().toString() %>'},
                            success: function (response) {
                                if (response.trim() !== "")
                                {
                                    alert(response);
                                } else
                                {
                                    window.location.href = 'EditarSesion.jsp';
                                }
                            },
                            error: function () {
                                alert('Error al procesar la solicitud');
                            }
                        });
                    });
                });
                $(document).ready(function () {
                    $('#BotonAñadirEntradas<%=s.getHora().toString().replaceAll(":", "")%>').click(function () {
                        $.ajax({
                            url: 'SesionesServlet',
                            type: 'POST',
                            data: {modo: 'redirigirAñadirEntrada', nombrePelicula: '<%=s.getNombrePelicula() %>',
                                        nombreSala: '<%=s.getNombreSala() %>', sesionHora: '<%=s.getHora().toString() %>',
                                        sesionFecha: '<%=s.getFecha().toString() %>'},
                            success: function (response) {
                                window.location.href = 'AnadirEntrada.jsp';
                            },
                            error: function () {
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
            F<%= e.getFila()%>C<%= e.getColumna()%>
            <button id="BotonEliminar<%=e.getFila()%><%=e.getColumna()%>">Eliminar Entrada</button>
            <button id="BotonEditar<%=e.getFila()%><%=e.getColumna()%>">Modificar Entrada</button>
            <script>
                $(document).ready(function () {
                    $('#BotonEliminar<%=e.getFila()%><%=e.getColumna()%>').click(function () {
                        var respuesta = confirm("Se va a realizar la siguiente acción: \n Borrar Entrada seleccionada. \n ¿Desea continuar?");
                        if (respuesta === true)
                        {
                            $.ajax({
                                url: 'SesionesServlet',
                                type: 'POST',
                                data: {modo: 'eliminarEntrada', nombrePelicula: '<%=e.getNombrePelicula() %>',
                                    nombreSala: '<%=e.getNombreSala() %>', entradaHora: '<%=e.getHora().toString() %>',
                                    entradaFecha: '<%=e.getFecha().toString() %>', entradaFila: '<%= e.getFila() %>',
                                    entradaColumna: '<%=e.getColumna() %>'},
                                success: function (response) {
                                    $.ajax({
                                        url: 'SesionesServlet',
                                        type: 'POST',
                                        data: {modo: 'buscarEntradas', nombrePelicula: '<%=e.getNombrePelicula() %>',
                                            nombreSala: '<%=e.getNombreSala() %>', sesionHora: '<%=e.getHora().toString() %>',
                                            sesionFecha: '<%=e.getFecha().toString() %>'},
                                        success: function (response) {
                                            window.location.href = 'AdminEntradas.jsp';
                                        },
                                        error: function () {
                                            alert('Error al procesar la solicitud');
                                        }
                                    });
                                },
                                error: function () {
                                    alert('Error al procesar la solicitud');
                                }
                            });
                        }
                    });
                });
                $(document).ready(function () {
                    $('#BotonEditar<%=e.getFila()%><%=e.getColumna()%>').click(function () {
                        $.ajax({
                            url: 'SesionesServlet',
                            type: 'POST',
                            data: {modo: 'redirigirEditarEntrada', nombrePelicula: '<%=e.getNombrePelicula() %>',
                                nombreSala: '<%=e.getNombreSala() %>', sesionHora: '<%=e.getHora().toString() %>',
                                sesionFecha: '<%=e.getFecha().toString() %>', entradaFila: '<%=e.getFila()%>',
                                entradaColumna: '<%=e.getColumna()%>'},
                            success: function (response) {
                                window.location.href = 'EditarEntrada.jsp';
                            },
                            error: function () {
                                alert('Error al procesar la solicitud');
                            }
                        });
                    });
                });
            </script>
            <br>
            <%}
        }
            %>
        </div>
        <script>
            var peliculaSeleccionada = $("#seleccionPelicula").val();
            var salaSeleccionada = $("#seleccionSala").val();
            $('#enunciadoSesiones').text('Sesiones de ' + peliculaSeleccionada + ' en ' + salaSeleccionada);
        </script>
        <script>
            $(document).ready(function () {
                $('#seleccionSala').change(function () {
                    salaSeleccionada = $(this).val();
                    $('#enunciadoSesiones').text('Sesiones de ' + peliculaSeleccionada + ' en ' + salaSeleccionada);
                    var formData = 'modo=buscar&pelicula=' + $("#seleccionPelicula").val() + '&sala=' + $("#seleccionSala").val();
                    $.ajax({
                        url: 'SesionesServlet',
                        type: 'POST',
                        data: formData,
                        success: function (response) {
                            window.location.href = 'AdminEntradas.jsp';
                        },
                        error: function () {
                            alert('Error al procesar la solicitud');
                        }
                    });
                });
            });
        </script>
        <script>
            $(document).ready(function () {
                $('#seleccionPelicula').change(function () {
                    peliculaSeleccionada = $(this).val();
                    $('#enunciadoSesiones').text('Sesiones de ' + peliculaSeleccionada + ' en ' + salaSeleccionada);
                    var formData = 'modo=buscar&pelicula=' + $("#seleccionPelicula").val() + '&sala=' + $("#seleccionSala").val();
                    $.ajax({
                        url: 'SesionesServlet',
                        type: 'POST',
                        data: formData,
                        success: function (response) {
                            window.location.href = 'AdminEntradas.jsp';
                        },
                        error: function () {
                            alert('Error al procesar la solicitud');
                        }
                    });
                });
            });
        </script>
    </body>
</html>