package BD;

import Controlador.ControladorHistorialMovimiento;
import Controlador.ControladorProductos;
import Controlador.ControladorUsuario;
import Modelo.HIstorialMovimientos;
import Modelo.Productos;
import Modelo.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cphes
 */
public class ConeccionTest {
    public static void main(String[] args) {
        ControladorUsuario controlador = new ControladorUsuario();
    String correo = "carlos123@gmail.com";
    String contrasenia = "Abc123";

        Usuario usuario= null;
        try {
            usuario = controlador.validarUsuario(correo, contrasenia);
        } catch (SQLException ex) {
            Logger.getLogger(ConeccionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    if (usuario != null) {
        System.out.println("Usuario validado: " + usuario.getNombre());
    } else {
        System.out.println("Usuario no encontrado o contraseña incorrecta.");
    }
        
        
        /* ControladorProductos controladorProductos = new ControladorProductos();
        ControladorHistorialMovimiento ch = new ControladorHistorialMovimiento();
          try {
        List<HIstorialMovimientos> historial = ch.obtenerHistorialMovimientos();
        for (HIstorialMovimientos h : historial) {
            System.out.println("Producto: " + h.getIdProducto().getNombre());
            System.out.println("Cantidad Anterior: " + h.getCantidadAnterior());
            System.out.println("Cantidad Nueva: " + h.getCantidadNueva());
            System.out.println("Usuario: " + h.getIdUsuario().getNombre() + " " + h.getIdUsuario().getApellido());
            System.out.println("Tipo de Movimiento: " + h.getTipoMovimiento());
            System.out.println("Fecha: " + h.getFecha());
            System.out.println("Hora: " + h.getHora());
            System.out.println("---------------");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        
      // Probar la inserción de un producto
        try {
            Productos nuevoProducto = new Productos();
            nuevoProducto.setNombre("Producto de Prueba");
            nuevoProducto.setTipo("Electrónica");
            nuevoProducto.setProvedor("Proveedor Test");
            nuevoProducto.setPrecio("150");
            nuevoProducto.setCantidad(100);
            nuevoProducto.setEstatus(1);

            boolean insercionExitosa = controladorProductos.insertarProducto(nuevoProducto, 1);
            if (insercionExitosa) {
                System.out.println("Producto insertado exitosamente.");
            } else {
                System.out.println("Error al insertar el producto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
/*
        // Probar la consulta de todos los productos
        try {
            List<Productos> listaProductos = controladorProductos.consultarProductoActivo();
            if (listaProductos != null && !listaProductos.isEmpty()) {
                System.out.println("Lista de productos:");
                for (Productos producto : listaProductos) {
                    System.out.println("ID: " + producto.getIdProducto() + ", Nombre: " + producto.getNombre());
                }
            } else {
                System.out.println("No hay productos disponibles.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
/*
        // Probar la modificación de un producto
        try {
            int idProducto = 1; // Camb0.ia esto por un ID válido
            int cantidadModificada = 50; // Cambia esto por la cantidad a modificar
            boolean modificacionExitosa = controladorProductos.salidaProducto(cantidadModificada, idProducto, 1);
            if (modificacionExitosa) {
                System.out.println("Producto modificado exitosamente.");
            } else {
                System.out.println("Error al modificar el producto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
/*
        // Probar la desactivación de un producto
        try {
            int idProductoDesactivar = 1; // Cambia esto por un ID válido
            boolean desactivacionExitosa = controladorProductos.activarProducto(idProductoDesactivar);
            if (desactivacionExitosa) {
                System.out.println("Producto desactivado exitosamente.");
            } else {
                System.out.println("Error al desactivar el producto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
