/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import BD.ConectarBD;
import Modelo.Roles;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author cphes
 */
public class ControladorUsuario {

    public Usuario validarUsuario(String nombre, String contrasenia) throws SQLException {
        ConectarBD conexion = new ConectarBD();
        Connection conn = conexion.open();
        String consulta = "select * from Usuario  where correo = ? and contrasenia = ? ;";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setString(1, nombre);
        ps.setString(2, contrasenia);
        ResultSet resultado = ps.executeQuery();
        Usuario u;

        if (resultado.next()) {
            u = Llenar(resultado);
        } else {
            u = null;
        }
        resultado.close();
        ps.close();
        conexion.close();
        return u;

    }

    public boolean validaUsuariov2(String usuario, String password) {

        return true;
    }

    private Usuario Llenar(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        Roles r = new Roles();
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setCorreo(rs.getString("correo"));
        u.setNombre(rs.getString("nombre"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setEstatus(rs.getInt("estatus"));
        u.setApellido(rs.getString("apellido"));
        u.setGafet(rs.getString("gafet"));
        r.setIdRol(rs.getInt("idRol"));
        u.setIdRol(r);

        return u;
    }

}
