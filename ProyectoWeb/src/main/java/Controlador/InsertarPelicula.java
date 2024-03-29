package Controlador;

import Utilitis.ModeloDatos;
import Utilitis.Pelicula;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

@MultipartConfig
public class InsertarPelicula extends HttpServlet {

    private ModeloDatos bd;
    //Poned la ruta de archivos donde guardais las imágenes en vuestro ordenador
    private final String rutaArchivos = "C:\\Users\\sergi\\OneDrive\\Escritorio\\ProyectoWeb\\ArquitecturaWeb\\ProyectoWeb\\src\\main\\webapp\\imagenes\\";
    private final File uploads = new File(rutaArchivos);

    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);

        HashMap<String, Pelicula> peliculasPorNombre = (HashMap) s.getAttribute("peliculas");

        String nombre = req.getParameter("nombrePelicula");

        if (!bd.existePelicula(nombre)) {

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
                    guardarImagen(portada, uploads), procesarActores(actores));

            bd.addPelicula(p);
            guardarActores(p.getNombre(), p.getActores());
            res.getWriter().print("");

        } else {
            PrintWriter out = res.getWriter();
            out.println("Ya hay una película con dicho nombre, pruebe otro.");
        }
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
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ruta;
    }

    private void guardarActores(String nombrePelicula, ArrayList<String> actores) {
        for (String actor : actores) {
            bd.addActor(nombrePelicula, actor);
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
