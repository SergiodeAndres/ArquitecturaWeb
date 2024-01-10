package Controlador;

import Utilitis.ModeloDatos;
import Utilitis.Pelicula;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;

public class CargarCartelera extends HttpServlet {
    
    private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException
    {
        bd=new ModeloDatos();
        bd.abrirConexion();
    }
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);

        HashMap<String, Pelicula> peliculasPorNombre = new HashMap<>();
        peliculasPorNombre.putAll(bd.getPeliculas());
        
        s.setAttribute("peliculas", peliculasPorNombre);
        
        res.sendRedirect(res.encodeRedirectURL("Cartelera.jsp"));
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
