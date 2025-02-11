package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDAO {
        private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque_java";
        // utilisateur MySQL on mofifie si différent
        private static final String USER = "root";
        // Le mot de passe MySQL ici
        private static final String PASSWORD = "";

        private static Connection conn;

        // Méthode pour touver la connexion
        public static Connection getConnection() throws SQLException {
            if (conn == null || conn.isClosed()) {
                try {
                    // Charger le driver MySQL
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Connexion MySQL établie !");
                } catch (ClassNotFoundException e) {
                    System.out.println("ERREUR : Driver MySQL introuvable !");
                    e.printStackTrace();
                } catch (SQLException e) {
                    System.out.println("ERREUR : Connexion MySQL échouée !");
                    e.printStackTrace();
                    throw e;
                }
            }
            return conn;
        }
    }
