package Controlador;

import Utilitis.ModeloDatos;
import Utilitis.Usuario;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

public class ServletRegistro extends HttpServlet {
     private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException
    {
        bd=new ModeloDatos();
        bd.abrirConexion();
    }
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);

        String nombre = req.getParameter("newUsername");
        String email = req.getParameter("newEmail");
        String contraseña = req.getParameter("newPassword");

        Usuario u = new Usuario(nombre, email, contraseña);

        if (!bd.ExisteUsuario(u))
        {
            bd.AddUsuario(new Usuario(u.getNombre(),u.getContraseña(),u.getCorreo()));
            res.getWriter().print("");
        }
        else 
        {
            PrintWriter out = res.getWriter();
            out.println("El usuario \"" + u.getNombre() + "\" ya existe.");
        }
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }

}
