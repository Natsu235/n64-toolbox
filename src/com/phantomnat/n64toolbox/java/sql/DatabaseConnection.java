/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dorian Pilorge
 */
public class DatabaseConnection {
    
    private String url = "jdbc:mysql://localhost/toolbox";
    private String username = "root";
    private String password = "";
    private static Connection connect;
    
    private DatabaseConnection() {
        try {
            connect = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Connection getInstance() {
        if (connect == null)
            new DatabaseConnection();
            
        return connect;
    }
    
}
