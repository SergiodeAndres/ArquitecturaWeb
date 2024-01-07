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
        <title>JSP Page</title>
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
        <h2>Ver Películas por (Categoría): </h2> 
        <button id="Genero">Género</button>
        <button id="Sala">Sala</button>
        <script>
            $(document).ready(function() {
                $('#Genero').click(function() {
                    var formData = 'modo=buscarGeneros';
                    $.ajax({
                        url: 'InformesServlet',
                        type: 'POST',
                        data: formData,
                        success: function(response) {
                            window.location.href = 'Informes.jsp';
                        },
                        error: function() {
                            alert('Error al procesar la solicitud');
                            }
                        });
                    });
                });
        </script>
            <%
            if (session.getAttribute("contenidoListaInforme") != null)
            {
                ArrayList<String> contenidos = (ArrayList<String>) session.getAttribute("contenidoListaInforme");
            %>
            <select id="seleccionInforme">
            <% 
                for(String s: contenidos) {
            %>
                <option value="<%=s%>"> <%=s%> </option>
            <%
                }
            %>
            </select>
            <%
            }
            %>
    </body>
</html>
