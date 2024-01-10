package Controlador;

import Utilitis.ModeloDatos;
import Utilitis.Pelicula;
import Utilitis.Sesion;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

public class CargarInformacionPelicula extends HttpServlet {
    
    private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException
    {
        bd=new ModeloDatos();
        bd.abrirConexion();
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        
        String nombrePeliculaSeleccionada = req.getParameter("peliculaSeleccionada");
        
        HashMap<String, Pelicula> peliculasPorNombre = (HashMap) s.getAttribute("peliculas");
        
        String username = (String) s.getAttribute("username");
        
        Pelicula peliculaSeleccionada = peliculasPorNombre.get(nombrePeliculaSeleccionada);
        
        s.setAttribute("datos_pelicula_seleccionada", peliculaSeleccionada);
        
        if (!(username == null)){
            ArrayList<Sesion> sesiones = new ArrayList<>(bd.getSesiones(nombrePeliculaSeleccionada));
        
            ArrayList<String> comentarios = new ArrayList<>(bd.getComentarios(peliculaSeleccionada.getNombre()));

            s.setAttribute("sesiones_pelicula_seleccionada", sesiones);

            s.setAttribute("comentarios_pelicula_seleccionada", comentarios);
        }          
    }
    
    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
