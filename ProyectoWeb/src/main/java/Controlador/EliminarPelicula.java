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
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

public class EliminarPelicula extends HttpServlet {

    private ModeloDatos bd;
    private final String rutaArchivosEliminar = "C:\\Users\\sergi\\OneDrive\\Escritorio\\ProyectoWeb\\ArquitecturaWeb\\ProyectoWeb\\src\\main\\webapp\\";

    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);

        String nombrePeliculaSeleccionada = req.getParameter("peliculaSeleccionada");

        if (!bd.existeSesionParaPelicula(nombrePeliculaSeleccionada)) {

            String rutaImagen = req.getParameter("imagen");

            bd.removeActoresPorPelicula(nombrePeliculaSeleccionada);
            bd.removeComentariosPorPelicula(nombrePeliculaSeleccionada);
            bd.removePelicula(nombrePeliculaSeleccionada);

            HashMap<String, Pelicula> peliculasPorNombre = (HashMap) s.getAttribute("peliculas");

            peliculasPorNombre.remove(nombrePeliculaSeleccionada);

            s.setAttribute("peliculas", peliculasPorNombre);

            eliminarImagen(rutaImagen);
            res.getWriter().print("");

        } else {

            PrintWriter out = res.getWriter();
            out.println("Esta película tiene sesiones asociadas, no se puede eliminar.");

        }
    }

    private void eliminarImagen(String rutaImagen) {
        try {
            rutaImagen = rutaImagen.replaceAll("/", "\\\\");

            String rutaAbsoluta = rutaArchivosEliminar + rutaImagen;

            File file = new File(rutaAbsoluta);

            if (file.exists()) {
                file.delete();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
