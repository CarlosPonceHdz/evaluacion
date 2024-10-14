/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Apis;

import Controlador.ControladorProductos;
import Modelo.Productos;
import com.google.gson.Gson;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cphes
 */
@Path("producto")
public class ApiProducto {
 @GET
@Path("consultarProducto")
@Produces(MediaType.APPLICATION_JSON)
public Response consultarProducto() {
     String respuesta = null; 
    try {
         Gson gson = new Gson();
         List<Productos> productos = null;
        
         ControladorProductos cl = new ControladorProductos();
         
         productos =cl.consultarTodosLosProductos();
          respuesta = new Gson().toJson(productos);
         
         
     } catch (SQLException ex) {
        ex.printStackTrace();
            respuesta = "{\"exception\":\"Error interno del servidor.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
     }
     return Response.status(Response.Status.OK).entity(respuesta).build();
}


@GET
@Path("consultarProductoActivos")
@Produces(MediaType.APPLICATION_JSON)
public Response consultarProductoActivos() {
     String respuesta = null; 
    try {
         Gson gson = new Gson();
         List<Productos> productos = null;
        
         ControladorProductos cl = new ControladorProductos();
         
         productos =cl.consultarProductoActivo();
          respuesta = new Gson().toJson(productos);
         
         
     } catch (SQLException ex) {
        ex.printStackTrace();
            respuesta = "{\"exception\":\"Error interno del servidor.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
     }
     return Response.status(Response.Status.OK).entity(respuesta).build();
}
@POST
 @Produces(MediaType.APPLICATION_JSON)
    @Path("insertar") // Endpoint para insertar producto
    public Response insertarProducto(@FormParam("producto") String productoJson, @FormParam("idUsuario") int idUsuario) {
    String respuesta;
    boolean exito;
    ControladorProductos cl = new ControladorProductos();
    
    // Convertir el JSON de producto a un objeto Productos
    Gson gson = new Gson();
    Productos producto = gson.fromJson(productoJson, Productos.class);
        try {
            exito = cl.insertarProducto(producto, idUsuario); // Llama al método de inserción
            if (exito) {
                respuesta = "{\"mensaje\":\"Producto insertado con éxito\"}";
                return Response.status(Response.Status.OK).entity(respuesta).build();
            } else {
                respuesta = "{\"error\":\"Error al insertar el producto\"}";
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = "{\"exception\":\"Error de conexión\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
        }
    }
    
    @POST
@Produces(MediaType.APPLICATION_JSON)
@Path("entrada") // Endpoint para entrada de producto
public Response entradaProducto(@FormParam("cantidad") int cantidad,
                                @FormParam("idProducto") int idProducto,
                                @FormParam("idUsuario") int idUsuario) {
    String respuesta;
    boolean exito;
    ControladorProductos controladorProductos = new ControladorProductos();
    
    try {
        exito = controladorProductos.entradaProducto(cantidad, idProducto, idUsuario);
        if (exito) {
            respuesta = "{\"mensaje\":\"Entrada de producto exitosa\"}";
            return Response.status(Response.Status.OK).entity(respuesta).build();
        } else {
            respuesta = "{\"error\":\"Error al realizar la entrada del producto\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        respuesta = "{\"exception\":\"Error de conexión\"}";
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
    }
}
@POST
@Produces(MediaType.APPLICATION_JSON)
@Path("salida") // Endpoint para salida de producto
public Response salidaProducto(@FormParam("cantidad") int cantidad,
                               @FormParam("idProducto") int idProducto,
                               @FormParam("idUsuario") int idUsuario) {
    String respuesta;
    boolean exito;
    ControladorProductos controladorProductos = new ControladorProductos();
    
    try {
        exito = controladorProductos.salidaProducto(cantidad, idProducto, idUsuario);
        if (exito) {
            respuesta = "{\"mensaje\":\"Salida de producto exitosa\"}";
            return Response.status(Response.Status.OK).entity(respuesta).build();
        } else {
            respuesta = "{\"error\":\"Error al realizar la salida del producto\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        respuesta = "{\"exception\":\"Error de conexión\"}";
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
    }
}

@POST
@Produces(MediaType.APPLICATION_JSON)
@Path("desactivar") // Endpoint para desactivar producto
public Response desactivarProducto(@FormParam("idProducto") int idProducto) {
    String respuesta;
    boolean exito;
    ControladorProductos controladorProductos = new ControladorProductos();
    
    try {
        exito = controladorProductos.desactivarProducto(idProducto);
        if (exito) {
            respuesta = "{\"mensaje\":\"Producto desactivado con éxito\"}";
            return Response.status(Response.Status.OK).entity(respuesta).build();
        } else {
            respuesta = "{\"error\":\"Error al desactivar el producto\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        respuesta = "{\"exception\":\"Error de conexión\"}";
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
    }
}

@POST
@Produces(MediaType.APPLICATION_JSON)
@Path("activar") // Endpoint para activar producto
public Response activarProducto(@FormParam("idProducto") int idProducto) {
    String respuesta;
    boolean exito;
    ControladorProductos controladorProductos = new ControladorProductos();
    
    try {
        exito = controladorProductos.activarProducto(idProducto);
        if (exito) {
            respuesta = "{\"mensaje\":\"Producto activado con éxito\"}";
            return Response.status(Response.Status.OK).entity(respuesta).build();
        } else {
            respuesta = "{\"error\":\"Error al activar el producto\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        respuesta = "{\"exception\":\"Error de conexión\"}";
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
    }
}

   
}
