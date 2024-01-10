package Controlador;

import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Utilitis.ModeloDatos;
import Utilitis.Sala;

public class SalasServlet extends HttpServlet {

    private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    public void service(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {
        if (req.getParameter("modo").equals("añadir")) {
            String nombre = req.getParameter("nombre");
            int filas = Integer.parseInt(req.getParameter("filas"));
            int columnas = Integer.parseInt(req.getParameter("columnas"));

            Sala sala = new Sala(nombre, filas, columnas);

            if (!bd.ExisteSala(sala)) {
                bd.AddSala(sala);
                res.getWriter().print("");
            } else {
                PrintWriter out = res.getWriter();
                out.println("La sala \"" + sala.getNombre() + "\" ya existe.");
            }
        } else if (req.getParameter("modo").equals("eliminar")) {
            String nombre = req.getParameter("nombreSala");
            if (bd.salaTieneSesiones(nombre)) {
                PrintWriter out = res.getWriter();
                out.println("No se puede eliminar la " + nombre + "porque tiene "
                        + " sesiones. Elimine primero todas las sesiones de esta sala.");
            } else {
                int filas = Integer.parseInt(req.getParameter("filasSala"));
                int columnas = Integer.parseInt(req.getParameter("columnasSala"));
                Sala sala = new Sala(nombre, filas, columnas);
                bd.RemoveSala(sala);
                res.getWriter().print("");
            }
        } else if (req.getParameter("modo").equals("modificar")) {
            String nombreAntiguo = req.getParameter("sala");
            String nombre = req.getParameter("Nuevonombre");
            if (bd.salaTieneSesiones(nombreAntiguo)) {
                PrintWriter out = res.getWriter();
                out.println("No se puede modificar la " + nombreAntiguo + "porque tiene "
                        + " sesiones. Elimine primero todas las sesiones de esta sala.");
            } else {
                int filas = Integer.parseInt(req.getParameter("Nuevofilas"));
                int columnas = Integer.parseInt(req.getParameter("Nuevocolumnas"));
                Sala sala = new Sala(nombre, filas, columnas);
                if (!bd.ExisteSala(sala)) {
                    bd.UpdateSala(sala, nombreAntiguo);
                    res.getWriter().print("");
                } else {
                    PrintWriter out = res.getWriter();
                    out.println("No se puede modificar la " + nombreAntiguo + "porque ya existe"
                            + "la " + nombre);
                }
            }
        }
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
