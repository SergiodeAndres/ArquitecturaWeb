package Utilitis;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.sql.*;
import java.util.Iterator;
import java.time.LocalDate;
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
    
    public Pelicula getPeliculaNombre(String nombre)
    {
        Pelicula peliculaSeleccionada = getPeliculas().get(nombre);
        return peliculaSeleccionada;
    }
    
    public void addPelicula (Pelicula pelicula){
        String query = "INSERT INTO PELICULA VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, pelicula.getNombre());
            queryCompleta.setString(2, pelicula.getSinopsis());
            queryCompleta.setString(3, pelicula.getPaginaoficial());
            queryCompleta.setString(4, pelicula.getTitulooriginal());
            queryCompleta.setString(5, pelicula.getGenero());
            queryCompleta.setString(6, pelicula.getNacionalidad());
            queryCompleta.setInt(7, pelicula.getDuracion());
            queryCompleta.setInt(8, pelicula.getAno());
            queryCompleta.setString(9, pelicula.getDistribuidora());
            queryCompleta.setString(10, pelicula.getDirector());
            queryCompleta.setString(11, pelicula.getClasificacion());
            queryCompleta.setString(12, pelicula.getImagen());
            
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No se ha añadido la película");
            System.out.println(ex);
        }
    }
    
    public void removePelicula (String nombrePelicula){
        String query = "DELETE FROM PELICULA WHERE NOMBRE=?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nombrePelicula);
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No ha eliminado la sala");
            
        }
    }
    
    public void updatePelicula (Pelicula pelicula, String antiguoNombrePelicula){
        String query = "UPDATE PELICULA SET NOMBRE=?, SINOPSIS=?, PAGINAOFICIAL=?, TITULOORIGINAL=?, GENERO=?, NACIONALIDAD=?,"
                + " DURACION=?, AÑO=?, DISTRIBUIDORA=?, DIRECTOR=?, CLASIFICACION=?, PORTADA=? WHERE NOMBRE=?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, pelicula.getNombre());
            queryCompleta.setString(2, pelicula.getSinopsis());
            queryCompleta.setString(3, pelicula.getPaginaoficial());
            queryCompleta.setString(4, pelicula.getTitulooriginal());
            queryCompleta.setString(5, pelicula.getGenero());
            queryCompleta.setString(6, pelicula.getNacionalidad());
            queryCompleta.setInt(7, pelicula.getDuracion());
            queryCompleta.setInt(8, pelicula.getAno());
            queryCompleta.setString(9, pelicula.getDistribuidora());
            queryCompleta.setString(10, pelicula.getDirector());
            queryCompleta.setString(11, pelicula.getClasificacion());
            queryCompleta.setString(12, pelicula.getImagen());
            queryCompleta.setString(13, antiguoNombrePelicula);
            
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No se ha modificado la película");
            System.out.println(ex);
        }
    }
    
    public void quitarConstraintActores(){
        String query = "ALTER TABLE PELICULATIENEACTOR DROP CONSTRAINT FK_NOMBREPELICULA";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No se ha eliminado la constrait de actor");
            System.out.println(ex);
        }
    }
    
    public void ponerConstraintActores(){
        String query = "ALTER TABLE PELICULATIENEACTOR ADD CONSTRAINT FK_NOMBREPELICULA"
                + " FOREIGN KEY (NOMBREPELICULA) REFERENCES PELICULA(NOMBRE)";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No se ha añadido la constrait de actor");
            System.out.println(ex);
        }
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
    
    public void addActor(String nombrePelicula, String actor){
        String query = "INSERT INTO PELICULATIENEACTOR VALUES (?, ?)";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nombrePelicula);
            queryCompleta.setString(2, actor);
            
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No se ha añadido el actor");
            System.out.println(ex);
        }
    }
    
    public void updateActores(String nuevoNombrePelicula, String antiguoNombrePelicula){
        String query = "UPDATE PELICULATIENEACTOR SET NOMBREPELICULA=? WHERE NOMBREPELICULA=?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nuevoNombrePelicula);
            queryCompleta.setString(2, antiguoNombrePelicula);
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No se han actualizado los actores");
            
        }
    }
    
    public void removeActoresPorPelicula (String nombrePelicula){
        String query = "DELETE FROM PELICULATIENEACTOR WHERE NOMBREPELICULA=?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nombrePelicula);
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No han eliminado los actores");
            
        }
    }
    
    public void removeActoresPorPeliculaActor (String nombrePelicula, String actor){
        String query = "DELETE FROM PELICULATIENEACTOR WHERE NOMBREPELICULA=? AND NOMBREACTOR=?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nombrePelicula);
            queryCompleta.setString(2, actor);
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No han eliminado los actores");
            
        }
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
    
    public Sala getSalaNombre(String nombre)
    {
        ArrayList<Sala> salas = getSalas(); 
        Sala salaSeleccionada = new Sala("", 0, 0); 
        for (Sala s: salas)
        {
            if (s.getNombre().equals(nombre))
            {
                salaSeleccionada.setNombre(s.getNombre());
                salaSeleccionada.setColumnas(s.getColumnas());
                salaSeleccionada.setFilas(s.getFilas());
            }
        }
        return salaSeleccionada;
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
    
    public boolean salaTieneSesiones (String nombreSala)
    {
        boolean veredicto = false; 
        String query = "SELECT * FROM Sesion WHERE nombresala = ?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nombreSala);
            ResultSet rs = queryCompleta.executeQuery();
            veredicto = rs.next();
        } 
        catch (SQLException ex) {
            System.out.println("No ha leido de la tabla");
            
        }
        System.out.println(veredicto);
        return veredicto;
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
    
    public boolean existeSesionParaPelicula(String nombrePelicula){
        boolean existe = false;
        int contador=0;
        try {
            Statement set = conexion.createStatement();
            ResultSet rs = set.executeQuery("SELECT NOMBRESALA FROM SESION WHERE NOMBREPELICULA = " + "'" + nombrePelicula + "'");
            while(rs.next()){
                contador++;
            }
            existe = contador>0;
            rs.close();
            set.close();
        } catch (Exception e) {
            System.out.println("No coge de la tabla");
            System.out.println(e);
        }
        return existe;
    }
    
    public void quitarConstraintComentarios(){
        String query = "ALTER TABLE PELICULATIENECOMENTARIO DROP CONSTRAINT FK_NOMBREPELICULA2";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No se ha eliminado la constrait de comentario");
            System.out.println(ex);
        }
    }
    
    public void ponerConstraintComentarios(){
        String query = "ALTER TABLE PELICULATIENECOMENTARIO ADD CONSTRAINT FK_NOMBREPELICULA2"
                + " FOREIGN KEY (NOMBREPELICULA) REFERENCES PELICULA(NOMBRE)";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No se ha añadido la constrait de comentario");
            System.out.println(ex);
        }
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
    
    public void updateComentarios(String nuevoNombrePelicula, String antiguoNombrePelicula){
        String query = "UPDATE PELICULATIENECOMENTARIO SET NOMBREPELICULA=? WHERE NOMBREPELICULA=?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nuevoNombrePelicula);
            queryCompleta.setString(2, antiguoNombrePelicula);
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No se han actualizado los comentarios");
            
        }
    }
    
    public void removeComentariosPorPelicula (String nombrePelicula){
        String query = "DELETE FROM PELICULATIENECOMENTARIO WHERE NOMBREPELICULA=?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nombrePelicula);
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No han eliminado los comentarios");
            
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
     
    public void AddSesion (Sesion sesion)
    {
        String query = "INSERT INTO Sesion VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setDate(1, sesion.getFecha());
            queryCompleta.setTime(2, sesion.getHora());
            queryCompleta.setString(3, sesion.getNombreSala());
            queryCompleta.setString(4, sesion.getNombrePelicula());
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No ha añadido la sala");
            
        }
    }
    
    public ArrayList<Sesion> getSesionesPeliculaSala (String pelicula, String sala)
    {
        ArrayList<Sesion> sesiones = new ArrayList<Sesion>(); 
        String query = "SELECT * FROM Sesion WHERE nombresala = ? AND nombrepelicula = ?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, sala);
            queryCompleta.setString(2, pelicula);
            ResultSet rs = queryCompleta.executeQuery();
            while (rs.next()) {
                Sesion s = new Sesion(rs.getDate(1), rs.getTime(2), 
                        rs.getString(3), rs.getString(4));
                sesiones.add(s);
            }
            rs.close();
        } 
        catch (SQLException ex) {
            System.out.println("No ha leido de la tabla");
        }
        Collections.sort(sesiones, new Comparator<Sesion>() {
            @Override
            public int compare(Sesion s1, Sesion s2) {
                int fechaComparacion = s1.getFecha().compareTo(s2.getFecha());
                if (fechaComparacion != 0) {
                    return fechaComparacion;
                } else {
                    return s1.getHora().compareTo(s2.getHora());
                }
            }
        });
        return sesiones;
    }
    
    public void removeSesionesPorPelicula (String nombrePelicula){
        String query = "DELETE FROM SESION WHERE NOMBREPELICULA=?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, nombrePelicula);
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No han eliminado las sesiones");
            
        }
    }
    
    public void AddEntrada (Entrada entrada)
    {
        String query = "INSERT INTO Entrada VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setDate(1, entrada.getFecha());
            queryCompleta.setTime(2, entrada.getHora());
            queryCompleta.setString(3, entrada.getNombreSala());
            queryCompleta.setInt(4, entrada.getFila());
            queryCompleta.setInt(5, entrada.getColumna());
            queryCompleta.setString(6, entrada.getNombrePelicula());
            queryCompleta.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("No ha añadido la entrada");
            
        }
    }
    
    public ArrayList<Entrada> getEntradasSesion (String pelicula, String sala, String fecha, String hora)
    {
        ArrayList<Entrada> entradas = new ArrayList<Entrada>(); 
        String query = "SELECT * FROM Entrada WHERE nombresala = ? AND nombrepelicula = ? AND fecha = ? AND hora = ?";
        try {
            PreparedStatement queryCompleta = conexion.prepareStatement(query);
            queryCompleta.setString(1, sala);
            queryCompleta.setString(2, pelicula);
            queryCompleta.setDate(3, Date.valueOf(fecha));
            queryCompleta.setTime(4, Time.valueOf(hora));
            ResultSet rs = queryCompleta.executeQuery();
            while (rs.next()) {
                Entrada e = new Entrada(rs.getDate(1), rs.getTime(2), 
                        rs.getString(3), rs.getInt(4), rs.getInt(5), 
                        rs.getString(6));
                entradas.add(e);
            }
        } 
        catch (SQLException ex) {
            System.out.println("No ha añadido la sala");
            
        }
        for (Entrada e: entradas)
                { 
                    System.out.println("Fila " + e.getFila() + " Columna" + e.getColumna());
                }
        return entradas; 
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (Exception e) {
        }
    }

}
