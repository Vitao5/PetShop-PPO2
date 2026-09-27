/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.petshop.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vitin
 */
public class Conexao {
    final private String driver = "org.postgresql.Driver";
    final private String url = "jdbc:postgresql://localhost:5432/" + "bd_petshop";
    final private String usuario = "user_aulas";
    final private String senha = "123456";
    
    public Connection conectar(){
        Connection conn = null;
        
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuario, senha);
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return conn;
    }
}
