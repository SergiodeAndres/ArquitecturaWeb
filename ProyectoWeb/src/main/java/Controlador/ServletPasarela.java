package Controlador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Utilitis.Entrada;
import Utilitis.ModeloDatos;
import Utilitis.Pelicula;
import Utilitis.Reserva;
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
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author paser
 */
public class ServletPasarela extends HttpServlet {
    
    private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException
    {
        bd=new ModeloDatos();
        bd.abrirConexion();
    }
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        
        String tarjeta = req.getParameter("textoTarjeta");
        ArrayList<String> listaAsientosReservados = (ArrayList) s.getAttribute("listaAsientosReservados");
        Double totalPago = listaAsientosReservados.size()*10.00;
        
        boolean validar = bd.comprobarTarjeta(tarjeta, totalPago);
        if (validar){
            Random r= new Random();
            String caracteres = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String referencia ="";
            for (int i = 0; i < 16; i++) {
                int indiceAleatorio = r.nextInt(caracteres.length());
                char caracterAleatorio = caracteres.charAt(indiceAleatorio);
                referencia += caracterAleatorio;
            }
            while (bd.existeReferencia(referencia)){
                referencia ="";
                for (int i = 0; i < 16; i++) {
                    int indiceAleatorio = r.nextInt(caracteres.length());
                    char caracterAleatorio = caracteres.charAt(indiceAleatorio);
                    referencia += caracterAleatorio;
                } 
            }
            String nombrePelicula= s.getAttribute("nombrePelicula").toString();
            String nombreSala= s.getAttribute("nombreSala").toString();
            Date fechaSesion= Date.valueOf(s.getAttribute("fechaSesion").toString());
            Time horaSesion= Time.valueOf(s.getAttribute("horaSesion").toString());
            ArrayList<String> filasReservadas = (ArrayList) s.getAttribute("filasReservadas");
            ArrayList<String> columnasReservadas = (ArrayList) s.getAttribute("columnasReservadas");
            ArrayList<Entrada> listaEntradas = new ArrayList<Entrada>();
            for (int i = 0;i < filasReservadas.size();i++){
                Entrada e=new Entrada(fechaSesion,horaSesion, nombreSala,Integer.parseInt(filasReservadas.get(i)),Integer.parseInt(columnasReservadas.get(i)),nombrePelicula);
                listaEntradas.add(e);
                bd.RemoveEntrada(e);
            }
            Reserva reserva = new Reserva(nombrePelicula,fechaSesion,horaSesion,nombreSala,referencia,listaEntradas);
            bd.insertarReserva(reserva);     
            for (int i=0; i< filasReservadas.size();i++){
                bd.insertarReservaTieneAsiento(referencia,Integer.parseInt(filasReservadas.get(i)),Integer.parseInt(columnasReservadas.get(i)));
            }
            bd.restarSaldo(tarjeta, totalPago);
            
            s.setAttribute("referencia",referencia);
            
            res.getWriter().print("");
        }else{
            PrintWriter out = res.getWriter();
            out.println("La tarjeta no es valida o no dispone del saldo suficiente");
        }
        
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
