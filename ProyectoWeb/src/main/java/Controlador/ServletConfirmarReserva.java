package Controlador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Utilitis.Entrada;
import Utilitis.ModeloDatos;
import Utilitis.Pelicula;
import Utilitis.Sala;
import Utilitis.Sesion;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author paser
 */
public class ServletConfirmarReserva extends HttpServlet {
    
    private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException
    {
        bd=new ModeloDatos();
        bd.abrirConexion();
    }
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);

        String nombrePelicula= req.getParameter("nombrePelicula");
        String nombreSala= req.getParameter("nombreSala");
        String fechaSesion= req.getParameter("fechaSesion");
        String horaSesion= req.getParameter("horaSesion");
        
        Sala sala = bd.getSalaNombre(nombreSala);
        
        ArrayList<Entrada> entradas = new ArrayList<>();
        entradas = (bd.getEntradasSesion(nombrePelicula,nombreSala,fechaSesion,horaSesion));
        
        s.setAttribute("entradasSesion", entradas);
        s.setAttribute("salaActual",sala);
        
        System.out.println("Me ejecuto");
        
        res.sendRedirect(res.encodeRedirectURL("PaginaReservas.jsp"));
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
