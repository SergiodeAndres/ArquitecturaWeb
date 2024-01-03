package Utilitis;

import java.util.HashMap;
import java.util.ArrayList;
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
                sinopsis = rs.getString(2);//T
                paginaoficial = rs.getString(3);//T
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
                
                System.out.println(actores);
                
                Pelicula pelicula = new Pelicula(nombre, sinopsis, paginaoficial, titulooriginal, genero, nacionalidad, 
                duracion, ano, distribuidora, director, clasificacion, imagen, actores);
                
                peliculasPorNombre.put(nombre, pelicula);
                
                actores.clear();
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            System.out.println("No coge de la tabla");
            System.out.println(e);
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
    
    public void UpdateSala (Sala sala, String nombreAntiguo){
        String query = "UPDATE SALA SET Nombre = ?, Filas = ?, Columnas = ? WHERE Nombre = ?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, sala.getNombre());
            queryCompleta.setInt(2, sala.getFilas());
            queryCompleta.setInt(3, sala.getColumnas());
            queryCompleta.setString(4, nombreAntiguo);
            queryCompleta.executeUpdate();
            //HABRA QUE TENER EN CUENTA SI EN OTRAS TABLAS APARECE EL NOMBRE DE LA SALA Y CAMBIARLO TAMBIEN
            //FUERZA BRUTA SEGURAMENTE
        } 
        catch (SQLException ex) {
            System.out.println("No ha cambiado la sala");
            
        }
    }
    
    public ArrayList<Sesion> getSesiones(String nombrePelicula){
        ArrayList<Sesion> sesiones = new ArrayList<>();
        Date fecha;
        Time hora;
        String nombreSala;
        String nombrePeli;
        
        try {
            Statement set = conexion.createStatement();
            ResultSet rs = set.executeQuery("SELECT * FROM SESION WHERE NOMBREPELICULA = " + "'" + nombrePelicula + "'");
            while (rs.next()) {
                fecha = rs.getDate(1);
                hora = rs.getTime(2);
                nombreSala = rs.getString(3);
                nombrePeli = rs.getString(4);
                
                Sesion sesion = new Sesion(fecha, hora, nombreSala, nombrePeli);
                
                sesiones.add(sesion);
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            System.out.println("No coge de la tabla");
            System.out.println(e);
        }
        
        return sesiones;
    }
    
    public ArrayList<String> getComentarios(String nombrePelicula){
        ArrayList<String> comentarios = new ArrayList<>();
        
        try {
            Statement set = conexion.createStatement();
            ResultSet rs = set.executeQuery("SELECT COMENTARIO FROM PELICULATIENECOMENTARIO WHERE NOMBREPELICULA = " + "'" + nombrePelicula + "'");
            while (rs.next()) {
                comentarios.add(rs.getString(1));
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            System.out.println("No coge de la tabla");
            System.out.println(e);
        }
        
        return comentarios;
    }
    
    public void addComentario(String nombrePelicula, String comentario){
        String query = "INSERT INTO PELICULATIENECOMENTARIO VALUES (?, ?)";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nombrePelicula);
            queryCompleta.setString(2, comentario);
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No ha añadido el comentario");
            
        }
    }
    
    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        abrirConexion();
        try
        {
        statement = conexion.createStatement();
        setResultado = statement.executeQuery("SELECT * FROM USUARIO");
        while (setResultado.next())
        {
        String nombre = setResultado.getString("Nombre");
        String email = setResultado.getString("Correo");
        String contraseña = setResultado.getString("Contraseña");
        Usuario u = new Usuario(nombre, contraseña,email);
        usuarios.add(u);
        }
        setResultado.close();
        statement.close();
        }
        catch(Exception e){
        System.out.println("No lee de la tabla");
        }
        return usuarios; 
    }
    
     public boolean ExisteUsuario (Usuario usuario)
    {
        boolean veredicto = false; 
        ArrayList<Usuario> usuarios = getUsuarios(); 
        for (Usuario u : usuarios)
        {
            if (usuario.getNombre().equals(u.getNombre()))
            {
                veredicto = true; 
            }
        }
        return veredicto; 
    }
     
    public boolean coincideContraseña (Usuario usuario)
    {
        boolean veredicto = false; 
        ArrayList<Usuario> usuarios = getUsuarios(); 
        for (Usuario u : usuarios)
        {
            if (usuario.getNombre().equals(u.getNombre()) && usuario.getContraseña().equals(u.getContraseña()))
            {
                veredicto = true; 
            }
        }
        return veredicto; 
    }
     
     public void AddUsuario (Usuario usuario){
        String query = "INSERT INTO Usuario VALUES (?, ?, ?)";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, usuario.getNombre());
            queryCompleta.setString(3, usuario.getContraseña());
            queryCompleta.setString(2, usuario.getCorreo());
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No ha añadido el usuario");
            
        }
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (Exception e) {
        }
    }

}
