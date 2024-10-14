/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import BD.ConectarBD;
import Modelo.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author yazmi
 */
public class ControladorProductos {
     public boolean insertarProducto(Productos producto, int idUsuario) throws SQLException {
        ConectarBD conexion = new ConectarBD();
        Connection conn = conexion.open();
         ControladorHistorialMovimiento chm = new ControladorHistorialMovimiento();
         boolean exito = false;
        try {
            String consultaProducto = "INSERT INTO Productos (nombre, tipo, provedor, precio, cantidad, estatus) "
                                    + "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(consultaProducto, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getTipo());
            ps.setString(3, producto.getProvedor());
            ps.setString(4, producto.getPrecio());
            ps.setInt(5, 0);
            ps.setInt(6, producto.getEstatus());
            
            ps.executeUpdate();
         ResultSet rs = ps.getGeneratedKeys();
         int idProducto =0;
            if (rs.next()) {
                idProducto = rs.getInt(1);
            }
      

        // Registrar el movimiento después de la inserción
            chm.registrarMovimiento("Ingreso", 0, 0, idProducto, idUsuario);
            exito = true; // Marcamos la inserción como exitosa
        
          ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conexion.close();
        }
        return exito; 
    }
     // Método para consultar todos los productos
    public List<Productos> consultarTodosLosProductos() throws SQLException {
        List<Productos> listaProductos = new ArrayList<>();
        ConectarBD conexion = new ConectarBD();
        Connection conn = conexion.open();

        try {
            String consulta = "SELECT * FROM Productos";
            PreparedStatement ps = conn.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Productos producto = LlenarProducto(rs);  // Llenar el objeto Productos
                listaProductos.add(producto);
            }

            rs.close();
            ps.close();
            return listaProductos;
        } catch (SQLException e) {
            e.printStackTrace();
            return listaProductos;  // Devuelve la lista vacía en caso de error
        } finally {
            conexion.close();
        }
    }

    // Método para llenar el objeto Productos
    private Productos LlenarProducto(ResultSet rs) throws SQLException {
        Productos producto = new Productos();
        producto.setIdProducto(rs.getInt("idProducto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setTipo(rs.getString("tipo"));
        producto.setProvedor(rs.getString("provedor"));
        producto.setPrecio(rs.getString("precio"));
        producto.setCantidad(rs.getInt("cantidad"));
        producto.setEstatus(rs.getInt("estatus"));

        return producto;
    }
    
    
     public boolean entradaProducto(int cantidad, int idProducto, int idUsuario) throws SQLException {
    ConectarBD conexion = new ConectarBD();
    Connection conn = conexion.open();
    boolean exito = false;
    ControladorHistorialMovimiento chm = new ControladorHistorialMovimiento();
    Productos p = obtenerProductoPorId(idProducto, conn);
    
    try {
        // Paso 1: Obtener la cantidad actual del producto
        int cantidadActual = p.getCantidad();

        // Paso 2: Modificar la cantidad en la tabla: Productos
        String actualizarConsulta = "UPDATE Productos SET cantidad = cantidad + ? WHERE idProducto = ?";
        PreparedStatement psActualizar = conn.prepareStatement(actualizarConsulta);
        psActualizar.setInt(1, cantidad);
        psActualizar.setInt(2, idProducto);
        int filasActualizadas = psActualizar.executeUpdate();

        if (filasActualizadas > 0) {
            // Paso 3: Llamar función guardarHistorialMovimiento
            exito = chm.registrarMovimiento("entrada", cantidadActual, cantidadActual + cantidad, idProducto, idUsuario);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        conexion.close();
    }
    return exito;
}

public boolean salidaProducto(int cantidad, int idProducto, int idUsuario) throws SQLException {
    ConectarBD conexion = new ConectarBD();
    Connection conn = conexion.open();
    boolean exito = false;
    ControladorHistorialMovimiento chm = new ControladorHistorialMovimiento();
    Productos p = obtenerProductoPorId(idProducto, conn);
    
    try {
        // Paso 1: Obtener la cantidad actual del producto
        int cantidadActual = p.getCantidad();

        // Verificar que hay suficiente cantidad para salir
     
        // Paso 2: Modificar la cantidad en la tabla: Productos
        String actualizarConsulta = "UPDATE Productos SET cantidad = cantidad - ? WHERE idProducto = ?";
        PreparedStatement psActualizar = conn.prepareStatement(actualizarConsulta);
        psActualizar.setInt(1, cantidad);
        psActualizar.setInt(2, idProducto);
        int filasActualizadas = psActualizar.executeUpdate();

        if (filasActualizadas > 0) {
            // Paso 3: Llamar función guardarHistorialMovimiento
            exito = chm.registrarMovimiento("salida", cantidadActual, cantidadActual - cantidad, idProducto, idUsuario);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        conexion.close();
    }
    return exito;
}
  

    // Método auxiliar para obtener un producto por su ID
    private Productos obtenerProductoPorId(int idProducto, Connection conn) throws SQLException {
        String consulta = "SELECT * FROM Productos WHERE idProducto = ?";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setInt(1, idProducto);
        ResultSet rs = ps.executeQuery();
        Productos producto = new Productos();

        if (rs.next()) {
            producto.setIdProducto(rs.getInt("idProducto"));
            producto.setCantidad(rs.getInt("cantidad"));
            // Puedes llenar otros campos si es necesario
        }

        rs.close();
        ps.close();
        return producto;
    }

    
    public boolean desactivarProducto(int idProducto) throws SQLException {
    ConectarBD conexion = new ConectarBD();
    Connection conn = conexion.open();
    boolean exito = false;

    try {
        String actualizarConsulta = "UPDATE Productos SET estatus = 0 WHERE idProducto = ?";
        PreparedStatement ps = conn.prepareStatement(actualizarConsulta);
        ps.setInt(1, idProducto);
        int filasActualizadas = ps.executeUpdate();

        if (filasActualizadas > 0) {
            exito = true;  // El producto fue desactivado
        }

        ps.close();
    } catch (SQLException e) {
        e.printStackTrace();
        exito = false;  // Hubo un error
    } finally {
        conexion.close();
    }

    return exito;
}
    
    public boolean activarProducto(int idProducto) throws SQLException {
    ConectarBD conexion = new ConectarBD();
    Connection conn = conexion.open();
    boolean exito = false;

    try {
        String actualizarConsulta = "UPDATE Productos SET estatus = 1 WHERE idProducto = ?";
        PreparedStatement ps = conn.prepareStatement(actualizarConsulta);
        ps.setInt(1, idProducto);
        int filasActualizadas = ps.executeUpdate();

        if (filasActualizadas > 0) {
            exito = true;  // El producto fue desactivado
        }

        ps.close();
    } catch (SQLException e) {
        e.printStackTrace();
        exito = false;  // Hubo un error
    } finally {
        conexion.close();
    }

    return exito;
}
public List<Productos> consultarProductoActivo() throws SQLException {
    ConectarBD conexion = new ConectarBD();
    Connection conn = conexion.open();
    List<Productos> listaProductos = new ArrayList<>();

    try {
        String consulta = "SELECT * FROM Productos WHERE estatus = 1";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Productos producto = LlenarProducto(rs);  // Llenar el objeto Productos
                listaProductos.add(producto);
        }

        rs.close();
        ps.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        conexion.close();
    }

    return listaProductos;
}

    
}
