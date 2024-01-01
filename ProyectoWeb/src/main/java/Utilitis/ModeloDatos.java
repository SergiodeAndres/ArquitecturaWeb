package Utilitis;

import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloDatos {

    private Connection conexion;
    private Statement statement;
    private ResultSet setResultado;

    public void abrirConexion() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conexion = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            System.out.println("Se ha conectado");
        } catch (Exception e) {
            System.out.println("No se ha conectado");
        }
    }
    
    public HashMap<String, Pelicula> getPeliculas(){
        HashMap<String, Pelicula> peliculasPorNombre = new HashMap<>();
        
        String nombre;
        String sinopsis;
        String paginaoficial;
        String titulooriginal;
        String genero;
        String nacionalidad;
        int duracion;
        int ano;
        String distribuidora;
        String director;
        String clasificacion;
        String imagen;
        ArrayList<String> actores = new ArrayList<>();
        
        try {
            Statement set = conexion.createStatement();
            ResultSet rs = set.executeQuery("SELECT * FROM PELICULA");
            while (rs.next()) {
                
                nombre = rs.getString(1);//T
                System.out.println(nombre);
                sinopsis = rs.getString(2);//T
                System.out.println(sinopsis);
                paginaoficial = rs.getString(3);//T
                System.out.println(paginaoficial);
                titulooriginal = rs.getString(4);//T
                System.out.println(titulooriginal);
                genero = rs.getString(5);//T
                System.out.println(genero);
                nacionalidad = rs.getString(6);//T
                System.out.println(nacionalidad);
                duracion = rs.getInt(7);
                System.out.println(duracion);
                ano = rs.getInt(8);
                System.out.println(ano);
                distribuidora = rs.getString(9);//T
                System.out.println(distribuidora);
                director = rs.getString(10);
                System.out.println(director);
                clasificacion = rs.getString(11);
                System.out.println(clasificacion);
                imagen = rs.getString(12);
                System.out.println(imagen);
                actores.addAll(getActores(nombre));
                
                Pelicula pelicula = new Pelicula(nombre, sinopsis, paginaoficial, titulooriginal, genero, nacionalidad, 
                duracion, ano, distribuidora, clasificacion, imagen, actores);
                
                peliculasPorNombre.put(nombre, pelicula);
                
                actores.clear();
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            System.out.println("No coge de la tabla");
            System.out.println(e);
        } finally{
            
        }
        
        return peliculasPorNombre;
    }

    public ArrayList<String> getActores(String nombrePelicula){
        ArrayList<String> actores = new ArrayList<>();
        try {
            Statement set = conexion.createStatement();
            ResultSet rs = set.executeQuery("SELECT NOMBREACTOR FROM PELICULATIENEACTOR WHERE NOMBREPELICULA = " + "'" + nombrePelicula + "'");
            while (rs.next()) {
                actores.add(rs.getString(1));
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            System.out.println("No coge de la tabla");
            System.out.println(e);
        }
        return actores;
    }
    
    public ArrayList<Sala> getSalas() {
        ArrayList<Sala> salas = new ArrayList<Sala>();
        abrirConexion();
        try
        {
        statement = conexion.createStatement();
        setResultado = statement.executeQuery("SELECT * FROM Sala");
        while (setResultado.next())
        {
        String nombre = setResultado.getString("Nombre");
        int filas = setResultado.getInt("filas");
        int columnas = setResultado.getInt("columnas");
        Sala sala = new Sala(nombre, filas, columnas);
        salas.add(sala);
        }
        setResultado.close();
        statement.close();
        }
        catch(Exception e){
        System.out.println("No lee de la tabla");
        }
        return salas; 
    }
    
    public boolean ExisteSala (Sala sala)
    {
        boolean veredicto = false; 
        ArrayList<Sala> salas = getSalas(); 
        for (Sala s : salas)
        {
            if (sala.getNombre().equals(s.getNombre()))
            {
                veredicto = true; 
            }
        }
        return veredicto; 
    }
    
    public void AddSala (Sala sala){
        String query = "INSERT INTO Sala VALUES (?, ?, ?)";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, sala.getNombre());
            queryCompleta.setInt(2, sala.getFilas());
            queryCompleta.setInt(3, sala.getColumnas());
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No ha añadido la sala");
            
        }
    }
    
    public void RemoveSala (Sala sala){
        String query = "DELETE FROM sala WHERE nombre=?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, sala.getNombre());
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No ha eliminado la sala");
            
        }
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (Exception e) {
        }
    }

}
