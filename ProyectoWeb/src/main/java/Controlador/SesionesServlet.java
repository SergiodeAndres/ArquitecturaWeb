/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.sql.Date;
import java.sql.Time;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Utilitis.ModeloDatos; 
import Utilitis.Sesion;
import Utilitis.Sala;
import Utilitis.Entrada;
import Utilitis.Pelicula;

public class SesionesServlet extends HttpServlet {
    private ModeloDatos bd;


    public void init(ServletConfig cfg) throws ServletException
    {
    bd=new ModeloDatos();
    bd.abrirConexion();
    }


    public void service(HttpServletRequest req,
    HttpServletResponse res) throws ServletException, IOException
    {
        if (req.getParameter("modo").equals("añadir"))
        {
            LocalDateTime fechaHora = LocalDateTime.parse(req.getParameter("fechaHora"));
            String pelicula = req.getParameter("pelicula");
            String sala = req.getParameter("sala"); 
            LocalDate fecha = LocalDate.of(fechaHora.getYear(), fechaHora.getMonthValue(), fechaHora.getDayOfMonth());
            LocalTime hora = LocalTime.of(fechaHora.getHour(), fechaHora.getMinute(), 0);
            Sesion sesion = new Sesion(Date.valueOf(fecha), Time.valueOf(hora), sala, pelicula);
            ArrayList<Sesion> sesionesExistentesSala = bd.getSesionesPeliculaSala(pelicula, sala);
            boolean puedeCrearSesion = true;
            for (Sesion s: sesionesExistentesSala)
            {
                Pelicula peliculaActual = bd.getPeliculaNombre(s.getNombrePelicula());
                if (Date.valueOf(fecha).equals(s.getFecha()) && Time.valueOf(hora).getTime() >= s.getHora().getTime() 
                        && Time.valueOf(hora).getTime() <= s.getHora().getTime() + (peliculaActual.getDuracion() * 60 * 1000))
                {
                    puedeCrearSesion = false;
                }
            }
            if (puedeCrearSesion)
            {
                bd.AddSesion(sesion);
                Sala salaSesion = bd.getSalaNombre(sala);
                for (int i = 1; i <= salaSesion.getFilas(); i++)
                {
                    for (int j = 1; j <= salaSesion.getColumnas(); j++)
                    {
                        Entrada entrada = new Entrada(Date.valueOf(fecha),Time.valueOf(hora),sala,i,j,pelicula);
                        bd.AddEntrada(entrada);
                    }
                }
                res.getWriter().print("");
            }
            else 
            {
                PrintWriter out = res.getWriter();
                out.println("No se puede añadir está sesión porque su hora "
                        + "de comienzo coincide con la proyección de otra película "
                        + "en esa sala.");
            }
        }
        else if (req.getParameter("modo").equals("buscar"))
        {
            String pelicula = req.getParameter("pelicula");
            String sala = req.getParameter("sala");
            ArrayList<Sesion> sesiones = bd.getSesionesPeliculaSala(pelicula, sala);
            HttpSession session = req.getSession(false);
            if (session != null)
            {
                session.setAttribute("sesionesPeliculaActualSalaActual", sesiones);
                session.setAttribute("salaActual", sala);
                session.setAttribute("peliculaActual", pelicula);
                session.setAttribute("entradaSesionActual", new ArrayList<Entrada>());
            }
            else
            {
                HttpSession nuevaSession = req.getSession(true);
                nuevaSession.setAttribute("sesionesPeliculaActualSalaActual", sesiones);
                nuevaSession.setAttribute("salaActual", sala);
                nuevaSession.setAttribute("peliculaActual", pelicula);
                nuevaSession.setAttribute("entradaSesionActual", new ArrayList<Entrada>());
            }
            //res.sendRedirect(res.encodeRedirectURL("AdminEntradas.jsp"));
        }
        else if (req.getParameter("modo").equals("buscarEntradas"))
        {
            String pelicula = req.getParameter("nombrePelicula");
            String sala = req.getParameter("nombreSala");
            String fecha = req.getParameter("sesionFecha");
            String hora = req.getParameter("sesionHora");
            ArrayList<Entrada> entradas = bd.getEntradasSesion(pelicula, sala, fecha, hora);
            HttpSession session = req.getSession(false);
            if (session != null)
            {
                session.setAttribute("entradaSesionActual", entradas);
            }
            else
            {
                HttpSession nuevaSession = req.getSession(true);
                nuevaSession.setAttribute("entradaSesionActual", entradas);
            }
        }
    }


    public void destroy()
    {
    bd.cerrarConexion();
    super.destroy();
    }
}
