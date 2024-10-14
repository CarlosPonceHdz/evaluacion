/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Apis;

import Controlador.ControladorUsuario;
import com.google.gson.Gson;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import Modelo.Usuario;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;

/**
 *
 * @author cphes
 */
@Path("usuario")

public class ApiUsuario {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("validarUsuario")
    public Response login(Usuario usuario) {
        Gson gson = new Gson();

        String respuesta = null;
        ControladorUsuario cl = new ControladorUsuario();
        Usuario u = null;

        try {
            u = cl.validarUsuario(usuario.getCorreo(), usuario.getContrasenia());
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = "{\"exception\":\"Error de conexion\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
        }
        if (u != null) {
            respuesta = new Gson().toJson(u);
        } else {
            respuesta = "{\"error\":\"Datos invalidos\"}";
        }

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }
    
@POST
@Path("validarUsuariov2")
@Produces(MediaType.APPLICATION_JSON)
public Response loginv2(@FormParam("usuario") String usuario, @FormParam("contrasenia") String password) {
    Gson gson = new Gson();

    String respuesta = null;
    ControladorUsuario cl = new ControladorUsuario();
    Usuario u = null;

    try {
        u = cl.validarUsuario(usuario, password);
  
        if (u != null) {
            // Si el usuario es válido, lo regresamos como JSON
            respuesta = gson.toJson(u);
            return Response.status(Response.Status.OK).entity(respuesta).build();
        } else {
            // Si no se encuentra el usuario o los datos no son válidos
            respuesta = "{\"error\":\"Datos inválidos\"}";
            return Response.status(Response.Status.UNAUTHORIZED).entity(respuesta).build();
        }
        
    } catch (Exception e) {
        // Captura cualquier excepción que ocurra durante la validación del usuario
        e.printStackTrace();
        respuesta = "{\"exception\":\"Error de conexión\"}";
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
    }
}




    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("version")
    public Response version() {
        String respuesta = "1.0.0.0";
        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

}
