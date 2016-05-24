package com.mixtri.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class ConnectionFactory {
    //static reference to itself
    private static ConnectionFactory instance = 
                new ConnectionFactory();
    
    /*String url = "jdbc:mysql://localhost";
    String user = "root";
    String password = "mixtri";
    String driverClass = "com.mysql.jdbc.Driver";*/
    
    
    String user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
    String DB_NAME = System.getenv("OPENSHIFT_APP_NAME");
    String url = "jdbc:mysql://"+System.getenv("OPENSHIFT_MYSQL_DB_HOST")+"/"+DB_NAME;
    String driverClass = "com.mysql.jdbc.Driver";
        
    //private constructor
    private ConnectionFactory() {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     
    public static ConnectionFactory getInstance()   {
        return instance;
    }
     
    public Connection getConnection() throws SQLException, 
    ClassNotFoundException {
    	
    	Connection connection = 
            DriverManager.getConnection(url, user, password);
        
        return connection;
    }   
}
