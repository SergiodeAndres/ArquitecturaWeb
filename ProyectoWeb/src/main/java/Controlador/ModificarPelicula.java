package Controlador;

import Utilitis.ModeloDatos;
import Utilitis.Pelicula;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig
public class ModificarPelicula extends HttpServlet {

    private ModeloDatos bd;
    private final String rutaArchivos = "C:\\Users\\sergi\\OneDrive\\Escritorio\\ProyectoWeb\\ArquitecturaWeb\\ProyectoWeb\\src\\main\\webapp\\imagenes\\";
    private final File uploads = new File(rutaArchivos);
    private final String rutaArchivosEliminar = "C:\\Users\\sergi\\OneDrive\\Escritorio\\ProyectoWeb\\ArquitecturaWeb\\ProyectoWeb\\src\\main\\webapp\\";

    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);

        Pelicula peliculaSeleccionada = (Pelicula) s.getAttribute("datos_pelicula_seleccionada");

        String nombre = peliculaSeleccionada.getNombre();

        if (!bd.existeSesionParaPelicula(nombre)) {

            String sinopsis = req.getParameter("sinopsisPelicula");
            String paginaOficial = req.getParameter("paginaOficialPelicula");
            String tituloOriginal = req.getParameter("tituloOriginalPelicula");
            String genero = req.getParameter("generoPelicula");
            String nacionalidad = req.getParameter("nacionalidadPelicula");
            int duracion = Integer.parseInt(req.getParameter("duracionPelicula"));
            int fecha = Integer.parseInt(req.getParameter("fechaPelicula"));
            String distribuidora = req.getParameter("distribuidoraPelicula");
            String director = req.getParameter("directorPelicula");
            String clasificacion = req.getParameter("clasificacionPelicula");
            Part portada = req.getPart("portadaPelicula");
            String actores = req.getParameter("actoresPelicula");

            Pelicula p = new Pelicula(nombre, sinopsis, paginaOficial, tituloOriginal, genero,
                    nacionalidad, duracion, fecha, distribuidora, director, clasificacion,
                    modificarImagen(portada, peliculaSeleccionada.getImagen()), procesarActores(actores));

            modificarActores(nombre, p.getActores(), peliculaSeleccionada.getActores());
            bd.updatePelicula(p);

            res.getWriter().print("");

        } else {
            PrintWriter out = res.getWriter();
            out.println("Esta película tiene sesiones asociadas, no se puede modificar.");
        }

    }

    private String modificarImagen(Part part, String rutaImagen) {
        eliminarImagen(rutaImagen);
        return guardarImagen(part, uploads);
    }

    private String guardarImagen(Part part, File uploads) {
        String ruta = "";

        try {
            Path path = Paths.get(part.getSubmittedFileName());

            String nombreArchivo = path.getFileName().toString();
            ruta = "imagenes/" + nombreArchivo;

            InputStream input = part.getInputStream();

            if (input != null) {
                File file = new File(uploads, nombreArchivo);
                Files.copy(input, file.toPath());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ruta;
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

    private void modificarActores(String nombrePelicula, ArrayList<String> nuevosActores, ArrayList<String> antiguosActores) {

        for (String antiguoActor : antiguosActores) {
            if (!nuevosActores.contains(antiguoActor)) {
                bd.removeActoresPorPeliculaActor(nombrePelicula, antiguoActor);
            }
        }

        for (String nuevoActor : nuevosActores) {
            if (!antiguosActores.contains(nuevoActor)) {
                bd.addActor(nombrePelicula, nuevoActor);
            }
        }
    }

    private ArrayList<String> procesarActores(String cadenaActores) {
        ArrayList<String> actores = new ArrayList<>();

        cadenaActores = cadenaActores.replaceAll("\\.", "");
        String[] partes = cadenaActores.split(", ");

        for (String parte : partes) {
            actores.add(parte);
        }

        return actores;
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
