/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Utilitis.Entrada;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Utilitis.ModeloDatos; 
import Utilitis.Sala;
import java.util.ArrayList;

public class InformesServlet extends HttpServlet {
    private ModeloDatos bd;


    public void init(ServletConfig cfg) throws ServletException
    {
    bd=new ModeloDatos();
    bd.abrirConexion();
    }


    public void service(HttpServletRequest req,
    HttpServletResponse res) throws ServletException, IOException
    {
        if (req.getParameter("modo").equals("buscarGeneros"))
        {
            ArrayList<String> generos = bd.getGeneros();
            HttpSession session = req.getSession(false);
            if (session != null)
            {
                session.setAttribute("contenidoListaInforme", generos);
                session.setAttribute("modobusqueda", "generos");
            }
        }
        else if (req.getParameter("modo").equals("buscarSalas"))
        {
            ArrayList<Sala> salas = bd.getSalas();
            ArrayList<String> salasNombre = new ArrayList<String>(); 
            for (Sala s : salas)
            {
                salasNombre.add(s.getNombre());
            }
            HttpSession session = req.getSession(false);
            if (session != null)
            {
                session.setAttribute("contenidoListaInforme", salasNombre);
                session.setAttribute("modobusqueda", "salas");
            }
        }
        else if (req.getParameter("modo").equals("verInforme"))
        {
            System.out.println("HOLA2");
            String contenidoBusqueda = req.getParameter("busqueda");
            String tipoBusqueda = "";
            HttpSession session = req.getSession(false);
                if (session != null)
                {
                    tipoBusqueda = (String) session.getAttribute("modobusqueda");
                    if (tipoBusqueda.equals("salas"))
                    {
                        session.setAttribute("peliculasInforme", bd.getPeliculasSala(contenidoBusqueda));
                    }
                    if (tipoBusqueda.equals("generos"))
                    {
                        session.setAttribute("peliculasInforme", bd.getPeliculasGenero(contenidoBusqueda));
                    }
                }
        }
    }


    public void destroy()
    {
    bd.cerrarConexion();
    super.destroy();
    }
}