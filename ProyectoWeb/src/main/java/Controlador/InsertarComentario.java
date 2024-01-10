package Controlador;

import Utilitis.ModeloDatos;
import Utilitis.Pelicula;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

public class InsertarComentario extends HttpServlet {

    private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);

        String comentario = req.getParameter("comentario");

        Pelicula peliculaSeleccionada = (Pelicula) s.getAttribute("datos_pelicula_seleccionada");

        bd.addComentario(peliculaSeleccionada.getNombre(), comentario);

        ArrayList<String> comentarios = (ArrayList) s.getAttribute("comentarios_pelicula_seleccionada");

        comentarios.add(comentario);

        s.setAttribute("comentarios_pelicula_seleccionada", comentarios);

        res.sendRedirect(res.encodeRedirectURL("InformacionPelicula.jsp"));
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
