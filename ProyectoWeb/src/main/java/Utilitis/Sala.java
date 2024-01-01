package Utilitis;

public class Sala {

    public Sala(String nombre, int filas, int columnas) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
    }
    private String nombre;
    private int filas;
    private int columnas;

    /**
     * Get the value of columnas
     *
     * @return the value of columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Set the value of columnas
     *
     * @param columnas new value of columnas
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }


    /**
     * Get the value of filas
     *
     * @return the value of filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Set the value of filas
     *
     * @param filas new value of filas
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }


    /**
     * Get the value of nombre
     *
     * @return the value of nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Set the value of nombre
     *
     * @param nombre new value of nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
