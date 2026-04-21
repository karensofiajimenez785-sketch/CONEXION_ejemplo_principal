import java.sql.*;

public class conexion {

    static Connection con;

    public static void main(String[] args) throws Exception {

        // Cargar driver y conectar
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:basedatos.db");
        System.out.println("Conectado a SQLite");

        // Crear tabla
        con.createStatement().execute(
            "CREATE TABLE IF NOT EXISTS personas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, edad INTEGER)"
        );

        // INSERT
        insertar("Ana", 25);
        insertar("Carlos", 30);
        insertar("Maria", 22);

        // SELECT
        listar();

        // UPDATE
        actualizar(1, "Ana García", 26);

        // SELECT
        listar();

        // DELETE
        eliminar(2);

        // SELECT
        listar();

        con.close();
    }

    static void insertar(String nombre, int edad) throws Exception {
        PreparedStatement ps = con.prepareStatement("INSERT INTO personas (nombre, edad) VALUES (?, ?)");
        ps.setString(1, nombre);
        ps.setInt(2, edad);
        ps.executeUpdate();
        System.out.println("Insertado: " + nombre);
    }

    static void listar() throws Exception {
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM personas");
        System.out.println("--- Lista ---");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " | " + rs.getString("nombre") + " | " + rs.getInt("edad"));
        }
    }

    static void actualizar(int id, String nombre, int edad) throws Exception {
        PreparedStatement ps = con.prepareStatement("UPDATE personas SET nombre = ?, edad = ? WHERE id = ?");
        ps.setString(1, nombre);
        ps.setInt(2, edad);
        ps.setInt(3, id);
        ps.executeUpdate();
        System.out.println("Actualizado ID: " + id);
    }

    static void eliminar(int id) throws Exception {
        PreparedStatement ps = con.prepareStatement("DELETE FROM personas WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Eliminado ID: " + id);
    }
}