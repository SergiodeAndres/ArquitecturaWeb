/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitis;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class Reserva {
    
    private String nombrePelicula;
    private Date fecha;
    private Time hora;
    private String nombreSala;
    private String referencia;
    private ArrayList entradas;

    public Reserva(String nombrePelicula, Date fecha, Time hora, String nombreSala, String referencia, ArrayList entradas) {
        this.nombrePelicula = nombrePelicula;
        this.fecha = fecha;
        this.hora = hora;
        this.nombreSala = nombreSala;
        this.referencia = referencia;
        this.entradas = entradas;
    }

    
    public ArrayList getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList entradas) {
        this.entradas = entradas;
    }

    
    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    
    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    
    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }
    
    public void addEntrada(int fila, int columna)
    {
        Entrada entrada = new Entrada(fecha, hora, nombreSala, fila, columna, nombrePelicula);
        entradas.add(entrada);
    }

}
