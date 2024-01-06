/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import java.util.HashMap;

/**
 *
 * @author paser
 */
public class EliminarPelicula extends HttpServlet {
private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException
    {
        bd=new ModeloDatos();
        bd.abrirConexion();
    }
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       HttpSession s = req.getSession(true);
        
        String nombrePeliculaSeleccionada = req.getParameter("peliculaSeleccionada");
        String rutaImagen = req.getParameter("imagen");
        
        bd.removeActoresPorPelicula(nombrePeliculaSeleccionada);
        bd.removeComentariosPorPelicula(nombrePeliculaSeleccionada);
        bd.removeSesionesPorPelicula(nombrePeliculaSeleccionada);
        bd.removePelicula(nombrePeliculaSeleccionada);
        
        HashMap<String, Pelicula> peliculasPorNombre = (HashMap) s.getAttribute("peliculas");
        
        peliculasPorNombre.remove(nombrePeliculaSeleccionada);
        
        s.setAttribute("peliculas", peliculasPorNombre);
        
        
        
        try{    
            rutaImagen = rutaImagen.replaceAll("/", "\\\\");
            
            System.out.println(rutaImagen);
            
            //Poned la ruta de archivos donde tangais la carpeta donde se guardan las imagenes
            String rutaAbsoluta = "C:\\Users\\paser\\OneDrive\\Documentos\\NetBeansProjects\\Cine\\src\\main\\webapp\\" + rutaImagen;
            
            System.out.println(rutaAbsoluta);
            
            File file = new File(rutaAbsoluta);
            
            if (file.exists()){
                if (file.delete()){
                    System.out.println("Archivo eliminado correctamente");
                }else{
                    System.out.println("Archivo no eliminado.");
                }
            }else{
                System.out.println("Archivo no existe.");
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
