package Utilitis;


import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author paser
 */
public class Pelicula {
    
    private String nombre;
    private String sinopsis;
    private String paginaoficial;
    private String titulooriginal;
    private String genero;
    private String nacionalidad;
    private int duracion;
    private int ano;
    private String distribuidora;
    private String director;
    private String clasificacion;
    private String imagen;
    private ArrayList<String> actores;

    public Pelicula(String nombre, String sinopsis, String paginaoficial, String titulooriginal, String genero, String nacionalidad, int duracion, int ano, String distribuidora, String director, String clasificacion, String imagen, ArrayList<String> actores) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.paginaoficial = paginaoficial;
        this.titulooriginal = titulooriginal;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
        this.duracion = duracion;
        this.ano = ano;
        this.distribuidora = distribuidora;
        this.director = director;
        this.clasificacion = clasificacion;
        this.imagen = imagen;
        this.actores = new ArrayList(actores);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPaginaoficial() {
        return paginaoficial;
    }

    public void setPaginaoficial(String paginaoficial) {
        this.paginaoficial = paginaoficial;
    }

    public String getTitulooriginal() {
        return titulooriginal;
    }

    public void setTitulooriginal(String titulooriginal) {
        this.titulooriginal = titulooriginal;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDistribuidora() {
        return distribuidora;
    }

    public void setDistribuidora(String distribuidora) {
        this.distribuidora = distribuidora;
    }
    
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<String> getActores() {
        return actores;
    }

    public void setActores(ArrayList<String> actores) {
        this.actores = actores;
    }
}
