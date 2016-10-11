package com.formation.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBService {
    private static final Logger logger = LogManager.getLogger("DBService");

    // Singleton
    private static DBService instance;

    private static String ipAdress="192.168.1.27";
    private Connection connection;

    public static DBService getInstance() {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    public static void configure(String ip){
        ipAdress= ip;
        logger.info("l'adresse de la bdd est fixé à " + ipAdress);
    }

    private DBService() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.1.27/ecommerce?user=root&password=formation&useSSL=false");
        } catch (ClassNotFoundException e) {
            System.err.println("Impossible de trouver le driver jdbc : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Impossible de se connecter à la base : " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement prepareStatement(String requete) throws SQLException {
        return connection.prepareStatement(requete);
    }

    public ResultSet executeSelect(String requete) throws SQLException {
        return createStatement().executeQuery(requete);
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO
            }
        }
    }
}
