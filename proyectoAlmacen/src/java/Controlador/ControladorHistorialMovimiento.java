/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import BD.ConectarBD;
import Modelo.HIstorialMovimientos;
import Modelo.Productos;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author yazmi
 */
public class ControladorHistorialMovimiento {
       public boolean registrarMovimiento(String tipoMovimiento, int cantidadAnterior, int cantidadNueva, int idProducto , int idUsuario) throws SQLException {
        ConectarBD conexion = new ConectarBD();
        Connection conn = conexion.open();

        try {
            // Obtener la fecha y la hora actuales
            LocalDate fechaActual = LocalDate.now();  // Solo la fecha
            LocalTime horaActual = LocalTime.now();   // Solo la hora

           HIstorialMovimientos historial = new HIstorialMovimientos();
            historial.setTipoMovimiento(tipoMovimiento);
            historial.setCantidadAnterior(cantidadAnterior);
            historial.setCantidadNueva(cantidadNueva);
            historial.setFecha(fechaActual.toString());
            historial.setHora(horaActual.toString());

            // Inicializa el objeto Usuario
            Usuario usuario = new Usuario(); // Asegúrate de tener una clase Usuario
            usuario.setIdUsuario(idUsuario);
            historial.setIdUsuario(usuario); // Suponiendo que tienes un método setIdUsuario en HIstorialMovimientos

            // Similar para Producto
            Productos producto = new Productos(); // Asegúrate de tener una clase Producto
            producto.setIdProducto(idProducto);
            historial.setIdProducto(producto);
            // Consulta SQL para insertar el historial
            String consultaHistorial = "INSERT INTO HistorialMovimientos (tipoMovimiento, fecha, hora, cantidadAnterior, cantidadNueva, idProducto,idUsuario) "
                                     + "VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement psHistorial = conn.prepareStatement(consultaHistorial);
            psHistorial.setString(1, historial.getTipoMovimiento());
            psHistorial.setDate(2, java.sql.Date.valueOf(fechaActual));  // Asignar la fecha como java.sql.Date
            psHistorial.setTime(3, java.sql.Time.valueOf(horaActual));   // Asignar la hora como java.sql.Time
            psHistorial.setInt(4, historial.getCantidadAnterior());
            psHistorial.setInt(5, historial.getCantidadNueva());
            psHistorial.setInt(6, historial.getIdProducto().getIdProducto());
            psHistorial.setInt(7,    historial.getIdUsuario().getIdUsuario());
             
            psHistorial.executeUpdate();
            psHistorial.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            conexion.close();
        }
    }
       
       public List<HIstorialMovimientos> obtenerHistorialMovimientos() throws SQLException {
        ConectarBD conexion = new ConectarBD();
        Connection conn = conexion.open();
        List<HIstorialMovimientos> historialList = new ArrayList<>();

        try {
            String consulta = "SELECT p.nombre AS nombre_producto, h.cantidadAnterior, h.cantidadNueva, u.gafet, u.nombre, u.apellido, h.tipoMovimiento, h.fecha, h.hora " +
                              "FROM HistorialMovimientos h " +
                              "JOIN Productos p ON h.idProducto = p.idProducto " +
                              "JOIN Usuario u ON h.idUsuario = u.idUsuario";

            PreparedStatement ps = conn.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Create instances of your model objects
                HIstorialMovimientos historial = new HIstorialMovimientos();
                Productos producto = new Productos();
                Usuario usuario = new Usuario();

                // Set product data
                producto.setNombre(rs.getString("nombre_producto"));
                
                // Set user data
                usuario.setGafet(rs.getString("gafet"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));

                // Set historial data
                historial.setCantidadAnterior(rs.getInt("cantidadAnterior"));
                historial.setCantidadNueva(rs.getInt("cantidadNueva"));
                historial.setTipoMovimiento(rs.getString("tipoMovimiento"));
                historial.setFecha(rs.getString("fecha"));
                historial.setHora(rs.getString("hora"));
                
                // Associate product and user with the historial
                historial.setIdProducto(producto);
                historial.setIdUsuario(usuario);

                // Add to the list
                historialList.add(historial);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close();
        }

        return historialList;
    }
}
