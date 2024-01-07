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
            }
        }
    }


    public void destroy()
    {
    bd.cerrarConexion();
    super.destroy();
    }
}