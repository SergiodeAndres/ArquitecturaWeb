/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Utilitis.ModeloDatos;
import Utilitis.Pelicula;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 *
 * @author paser
 */
@MultipartConfig
public class ModificarPelicula extends HttpServlet {

    private ModeloDatos bd;
    private String rutaArchivos = "C:\\Users\\paser\\OneDrive\\Documentos\\NetBeansProjects\\Cine\\src\\main\\webapp\\imagenes\\";
    private File uploads = new File(rutaArchivos);
    private String rutaArchivosEliminar = "C:\\Users\\paser\\OneDrive\\Documentos\\NetBeansProjects\\Cine\\src\\main\\webapp\\";
    
    public void init(ServletConfig cfg) throws ServletException
    {
        bd=new ModeloDatos();
        bd.abrirConexion();
    }
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        
        Pelicula peliculaSeleccionada = (Pelicula) s.getAttribute("datos_pelicula_seleccionada");
        HashMap <String, Pelicula> peliculasPorNombre = (HashMap) s.getAttribute("peliculas");

        String nombre = req.getParameter("nombrePelicula");
            
        System.out.println(peliculasPorNombre.keySet().toString());
            
        System.out.println(nombre);
            
        System.out.println(peliculasPorNombre.containsKey(nombre));

        if (!peliculasPorNombre.containsKey(nombre)){

            if (!bd.existeSesionParaPelicula(peliculaSeleccionada.getNombre())){

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

                System.out.println("Tomo los parámetros del req");
                    
                Pelicula p = new Pelicula(nombre, sinopsis, paginaOficial, tituloOriginal, genero, 
                        nacionalidad, duracion, fecha, distribuidora, director, clasificacion, 
                        modificarImagen(portada, peliculaSeleccionada.getImagen()), procesarActores(actores));

                bd.quitarConstraintActores();
                bd.quitarConstraintComentarios();

                bd.updateComentarios(nombre, peliculaSeleccionada.getNombre());
                modificarActores(nombre, peliculaSeleccionada.getNombre(),
                        p.getActores(), peliculaSeleccionada.getActores());
                bd.updatePelicula(p, peliculaSeleccionada.getNombre());

                bd.ponerConstraintActores();
                bd.ponerConstraintComentarios();

                peliculasPorNombre.remove(peliculaSeleccionada.getNombre());

                peliculasPorNombre.put(nombre, p);

                s.setAttribute("peliculas", peliculasPorNombre);

                } else{
                    PrintWriter out = res.getWriter();
                    out.println("Esta película tiene sesiones asociadas, no se puede modificar.");
                }

            } else {
                PrintWriter out = res.getWriter();
                out.println("Ya hay una película con dicho nombre, pruebe otro.");
            }
        

        res.sendRedirect(res.encodeRedirectURL("AdminCartelera.jsp"));
    }
    
    private String modificarImagen(Part part, String rutaImagen){
        eliminarImagen(rutaImagen);
        return guardarImagen(part, uploads);
    }
    
    private String guardarImagen(Part part, File uploads){
        String ruta = "";
        
        try{
            Path path = Paths.get(part.getSubmittedFileName());
            
            String nombreArchivo = path.getFileName().toString();
            ruta = "imagenes/" + nombreArchivo;
            
            InputStream input = part.getInputStream();
            
            if (input != null){
                File file = new File(uploads, nombreArchivo);
                Files.copy(input, file.toPath());
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return ruta;
    }
    
    private void eliminarImagen(String rutaImagen){
        try{    
            rutaImagen = rutaImagen.replaceAll("/", "\\\\");
            
            System.out.println(rutaImagen);
            
            String rutaAbsoluta = rutaArchivosEliminar + rutaImagen;
            
            System.out.println(rutaAbsoluta);
            
            File file = new File(rutaAbsoluta);
            
            if (file.exists()){
                file.delete();
            }
            
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void modificarActores(String nuevoNombrePelicula, String antiguoNombrePelicula, ArrayList<String> nuevosActores, ArrayList<String> antiguosActores){
        System.out.println(nuevoNombrePelicula);
        System.out.println(antiguoNombrePelicula);
        
        for (String antiguoActor:antiguosActores){
            if (!nuevosActores.contains(antiguoActor)){
                bd.removeActoresPorPeliculaActor(antiguoNombrePelicula, antiguoActor);
            }
        }
        
        bd.updateActores(nuevoNombrePelicula, antiguoNombrePelicula);
        
        for (String nuevoActor:nuevosActores){
            if (!antiguosActores.contains(nuevoActor)){
                bd.addActor(nuevoNombrePelicula, nuevoActor);
            }
        }
    }
    
    private ArrayList<String> procesarActores(String cadenaActores){
        ArrayList<String> actores = new ArrayList<>();
        
            cadenaActores = cadenaActores.replaceAll("\\.", "");
            String[] partes = cadenaActores.split(", ");
            
            for (String parte:partes){
                actores.add(parte);
            }
              
        return actores;
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}