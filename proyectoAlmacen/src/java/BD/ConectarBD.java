/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author cphes
 */
public class ConectarBD {
 Connection conn;
     public Connection open(){
        String user = "root";
        String password = "art4ever";
        String url="jdbc:mysql://127.0.0.1:3306/almacen?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf-8";
       
       try {
              Class.forName("com.mysql.cj.jdbc.Driver");
            conn =  DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            System.out.println("Error de conexion."+e.toString());
            throw new RuntimeException(e);
        }
    }
    
     public void close()
    {
        if (conn!= null) 
        {
            try {
                conn.close();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                System.out.println("Error en cierre de conexion."+e.toString());
            }
        }
    }

}
