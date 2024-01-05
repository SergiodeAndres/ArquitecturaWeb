/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitis;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author paser
 */
public class Sesion {
    
    private Date fecha;
    private Time hora;
    private String nombreSala;
    private String nombrePelicula;

    public Sesion(Date fecha, Time hora, String nombreSala, String nombrePelicula) {
        this.fecha = fecha;
        this.hora = hora;
        this.nombreSala = nombreSala;
        this.nombrePelicula = nombrePelicula;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }   

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }
    
    
}