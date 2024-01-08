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
            ArrayList<Sesion> sesionesExistentesSala = bd.getSesionesSala(sala);
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
        else if (req.getParameter("modo").equals("eliminarSesion"))
        {
            String pelicula = req.getParameter("nombrePelicula");
            String sala = req.getParameter("nombreSala"); 
            Date fecha = Date.valueOf(req.getParameter("sesionFecha"));
            Time hora = Time.valueOf(req.getParameter("sesionHora"));
            Sesion sesion = new Sesion(fecha, hora, sala, pelicula);
            if(!bd.sesionTieneReservas(sesion))
            {
                bd.RemoveSesion(sesion);
                Sala salaSesion = bd.getSalaNombre(sala);
                for (int i = 1; i <= salaSesion.getFilas(); i++)
                {
                    for (int j = 1; j <= salaSesion.getColumnas(); j++)
                    {
                        Entrada entrada = new Entrada(fecha,hora,sala,i,j,pelicula);
                        bd.RemoveEntrada(entrada);
                    }
                }
                res.getWriter().print("");
            }
            else 
            {
                PrintWriter out = res.getWriter();
                out.println("No se puede eliminar la sesión porque tiene reservas.");
            }
        }
        else if (req.getParameter("modo").equals("redirigirEditarSesion"))
        {
            
            String pelicula = req.getParameter("nombrePelicula");
            String sala = req.getParameter("nombreSala"); 
            Date fecha = Date.valueOf(req.getParameter("sesionFecha"));
            Time hora = Time.valueOf(req.getParameter("sesionHora"));
            Sesion sesion = new Sesion(fecha, hora, sala, pelicula);
            if(!bd.sesionTieneReservas(sesion))
            {
                HttpSession session = req.getSession(false);
                if (session != null)
                {
                    session.setAttribute("sesionActual", sesion);
                }
                res.getWriter().print("");
            }
            else 
            {
                PrintWriter out = res.getWriter();
                out.println("No se puede editar la sesión porque tiene reservas.");
            }
        }
        else if (req.getParameter("modo").equals("editarSesion"))
        {
            LocalDateTime fechaHora = LocalDateTime.parse(req.getParameter("fechaHora"));
            String pelicula = req.getParameter("pelicula");
            String sala = req.getParameter("sala"); 
            LocalDate fecha = LocalDate.of(fechaHora.getYear(), fechaHora.getMonthValue(), fechaHora.getDayOfMonth());
            LocalTime hora = LocalTime.of(fechaHora.getHour(), fechaHora.getMinute(), 0);
            Sesion sesion = new Sesion(Date.valueOf(fecha), Time.valueOf(hora), sala, pelicula);
            
            HttpSession session = req.getSession(false);
            Sesion sesionAntigua = sesion;
            if (session != null)
            {
                sesionAntigua = (Sesion) session.getAttribute("sesionActual");
            }
            
            ArrayList<Sesion> sesionesExistentesSala = bd.getSesionesSala(sala);
            boolean puedeEditarSesion = true;
            for (Sesion s: sesionesExistentesSala)
            {
                Pelicula peliculaActual = bd.getPeliculaNombre(s.getNombrePelicula());
                if (Date.valueOf(fecha).equals(s.getFecha()) && Time.valueOf(hora).getTime() >= s.getHora().getTime() 
                        && Time.valueOf(hora).getTime() <= s.getHora().getTime() + (peliculaActual.getDuracion() * 60 * 1000))
                {
                    puedeEditarSesion = false;
                }
            }
            if (puedeEditarSesion)
            {
                bd.UpdateSesion(sesion, sesionAntigua);
                //QUITAR ANTIGUAS
                ArrayList<Entrada> entradasAntiguas = bd.getEntradasSesion(
                        sesionAntigua.getNombrePelicula(), sesionAntigua.getNombreSala(),
                        sesionAntigua.getFecha().toString(), sesionAntigua.getHora().toString());
                for (Entrada e: entradasAntiguas)
                {
                    bd.RemoveEntrada(e);
                }
                //AÑADIR NUEVAS
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
                out.println("No se puede añadir esta sesión porque su hora "
                        + "de comienzo coincide con la proyección de otra película "
                        + "en esa sala.");
            }
        }
        else if (req.getParameter("modo").equals("eliminarEntrada"))
        {
            String pelicula = req.getParameter("nombrePelicula");
            String sala = req.getParameter("nombreSala"); 
            Date fecha = Date.valueOf(req.getParameter("entradaFecha"));
            Time hora = Time.valueOf(req.getParameter("entradaHora"));
            int fila = Integer.parseInt(req.getParameter("entradaFila"));
            int columna = Integer.parseInt(req.getParameter("entradaColumna"));
            Entrada entrada = new Entrada(fecha, hora, sala, fila, columna, pelicula);
            bd.RemoveEntrada(entrada);
        }
        else if (req.getParameter("modo").equals("redirigirAñadirEntrada"))
        {
            String pelicula = req.getParameter("nombrePelicula");
            String sala = req.getParameter("nombreSala"); 
            Date fecha = Date.valueOf(req.getParameter("sesionFecha"));
            Time hora = Time.valueOf(req.getParameter("sesionHora"));
            Sesion sesion = new Sesion(fecha, hora, sala, pelicula);
            HttpSession session = req.getSession(false);
            if (session != null)
            {
                session.setAttribute("sesionActual", sesion);
            }
        }
        else if (req.getParameter("modo").equals("añadirEntrada"))
        {
            Sesion sesion = new Sesion(Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), "a", "a");
            HttpSession session = req.getSession(false);
            if (session != null)
            {
                sesion = (Sesion) session.getAttribute("sesionActual");
            }
            int columna = Integer.parseInt(req.getParameter("columna"));
            int fila = Integer.parseInt(req.getParameter("fila"));
            Entrada entrada = new Entrada(sesion.getFecha(), sesion.getHora(), sesion.getNombreSala(),
            fila, columna, sesion.getNombrePelicula());
            if (!bd.ExisteEntrada(entrada))
            {
                bd.AddEntrada(entrada);
                res.getWriter().print("");
            }
            else 
            {
                PrintWriter out = res.getWriter();
                out.println("No se puede añadir esta entrada porque ya existe una identica. Dicha entrada puede encontrarse en una reserva.");
            }
        }
        else if (req.getParameter("modo").equals("redirigirEditarEntrada"))
        {
            String pelicula = req.getParameter("nombrePelicula");
            String sala = req.getParameter("nombreSala"); 
            Date fecha = Date.valueOf(req.getParameter("sesionFecha"));
            Time hora = Time.valueOf(req.getParameter("sesionHora"));
            int fila = Integer.parseInt(req.getParameter("entradaFila"));
            int columna = Integer.parseInt(req.getParameter("entradaColumna"));
            Sesion sesion = new Sesion(fecha, hora, sala, pelicula);
            Entrada entrada = new Entrada(fecha, hora, sala, fila, columna, pelicula);
            HttpSession session = req.getSession(false);
            if (session != null)
            {
                session.setAttribute("entradaAntigua", entrada);
                session.setAttribute("sesionActual", sesion);
                session.setAttribute("salaActualEntrada", bd.getSalaNombre(sala));
            }
        }
        else if (req.getParameter("modo").equals("actualizarSala"))
        {
            HttpSession session = req.getSession(false);
            if (session != null)
            {
                session.setAttribute("salaActualEntrada", bd.getSalaNombre(req.getParameter("sala")));
            }
        }
        else if (req.getParameter("modo").equals("editarEntrada"))
        {
            LocalDateTime fechaHora = LocalDateTime.parse(req.getParameter("fechaHora"));
            String pelicula = req.getParameter("pelicula");
            String sala = req.getParameter("sala"); 
            int fila = Integer.parseInt(req.getParameter("fila"));
            int columna = Integer.parseInt(req.getParameter("columna"));
            LocalDate fecha = LocalDate.of(fechaHora.getYear(), fechaHora.getMonthValue(), fechaHora.getDayOfMonth());
            LocalTime hora = LocalTime.of(fechaHora.getHour(), fechaHora.getMinute(), 0);
            Sesion sesion = new Sesion(Date.valueOf(fecha), Time.valueOf(hora), sala, pelicula);
            Entrada entradaAntigua = new Entrada(Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            "a", 1, 1, "a");
            HttpSession session = req.getSession(false);
            if (session != null)
            {
                entradaAntigua = (Entrada) session.getAttribute("entradaAntigua");
            }
            if (bd.ExisteSesion(sesion))
            {
                Entrada entrada = new Entrada(Date.valueOf(fecha), Time.valueOf(hora), sala, fila, columna, pelicula);
                if(!bd.ExisteEntrada(entrada))
                {
                    bd.AddEntrada(entrada);
                    bd.RemoveEntrada(entradaAntigua);
                    res.getWriter().print("");
                }
                else 
                {
                    PrintWriter out = res.getWriter();
                    out.println("No se puede modificar esta entrada. La entrada resultante ya existe.");
                }
            }
            else 
            {
                PrintWriter out = res.getWriter();
                out.println("No se puede modificar esta entrada. La sesión no existe.");
            }
        }
    }


    public void destroy()
    {
    bd.cerrarConexion();
    super.destroy();
    }
}
