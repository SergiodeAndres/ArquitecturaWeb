package Controlador;

import Utilitis.Entrada;
import Utilitis.ModeloDatos;
import Utilitis.Sala;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

public class ServletReserva extends HttpServlet {

    private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);

        String nombrePelicula = req.getParameter("nombrePelicula");
        String nombreSala = req.getParameter("nombreSala");
        String fechaSesion = req.getParameter("fechaSesion");
        String horaSesion = req.getParameter("horaSesion");

        Sala sala = bd.getSalaNombre(nombreSala);

        ArrayList<Entrada> entradas = new ArrayList<>();
        entradas = (bd.getEntradasSesion(nombrePelicula, nombreSala, fechaSesion, horaSesion));

        s.setAttribute("entradasSesion", entradas);
        s.setAttribute("salaActual", sala);
        s.setAttribute("nombrePelicula", nombrePelicula);
        s.setAttribute("nombreSala", nombreSala);
        s.setAttribute("fechaSesion", fechaSesion);
        s.setAttribute("horaSesion", horaSesion);

        res.sendRedirect(res.encodeRedirectURL("PaginaReservas.jsp"));
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
