/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Apis;

import Controlador.ControladorHistorialMovimiento;
import Modelo.HIstorialMovimientos;
import Modelo.Productos;
import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cphes
 */
@Path("historial")
public class ApiHistorialMoviminetos {
     @GET
@Path("consultarHistorial")
@Produces(MediaType.APPLICATION_JSON)
public Response consultarProducto() {
     String respuesta = null; 
    try {
         Gson gson = new Gson();
         List<HIstorialMovimientos> historial = null;
        
         ControladorHistorialMovimiento cl = new ControladorHistorialMovimiento();
         
         historial =cl.obtenerHistorialMovimientos();
          respuesta = new Gson().toJson(historial);
         
         
     } catch (SQLException ex) {
        ex.printStackTrace();
            respuesta = "{\"exception\":\"Error interno del servidor.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
     }
     return Response.status(Response.Status.OK).entity(respuesta).build();
}
}
