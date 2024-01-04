package Utilitis;
import java.sql.Date;
import java.sql.Time;
/**
 *
 * @author sergi
 */
public class Entrada {
    
    private Date fecha;
    private Time hora;
    private String nombreSala;
    private int fila;
    private int columna;
    private String nombrePelicula;

    public Entrada(Date fecha, Time hora, String nombreSala, int fila, int columna, String nombrePelicula) {
        this.fecha = fecha;
        this.hora = hora;
        this.nombreSala = nombreSala;
        this.fila = fila;
        this.columna = columna;
        this.nombrePelicula = nombrePelicula;
    }

    /**
     * Get the value of nombrePelicula
     *
     * @return the value of nombrePelicula
     */
    public String getNombrePelicula() {
        return nombrePelicula;
    }

    /**
     * Set the value of nombrePelicula
     *
     * @param nombrePelicula new value of nombrePelicula
     */
    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }


    /**
     * Get the value of columna
     *
     * @return the value of columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Set the value of columna
     *
     * @param columna new value of columna
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }


    /**
     * Get the value of fila
     *
     * @return the value of fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * Set the value of fila
     *
     * @param fila new value of fila
     */
    public void setFila(int fila) {
        this.fila = fila;
    }


    /**
     * Get the value of nombreSala
     *
     * @return the value of nombreSala
     */
    public String getNombreSala() {
        return nombreSala;
    }

    /**
     * Set the value of nombreSala
     *
     * @param nombreSala new value of nombreSala
     */
    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }


    /**
     * Get the value of hora
     *
     * @return the value of hora
     */
    public Time getHora() {
        return hora;
    }

    /**
     * Set the value of hora
     *
     * @param hora new value of hora
     */
    public void setHora(Time hora) {
        this.hora = hora;
    }


    /**
     * Get the value of fecha
     *
     * @return the value of fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Set the value of fecha
     *
     * @param fecha new value of fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
