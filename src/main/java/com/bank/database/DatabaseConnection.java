package com.bank.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/TransactionBanquaire";  // Remplacez par votre URL de base de données
    private static final String USER = "root";  // Utilisateur de la base de données
    private static final String PASSWORD = "";  // Mot de passe de la base de données

    // Méthode pour obtenir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        try {
            // Enregistrer le driver MySQL (à partir de Java 6, il est automatique, donc cette ligne peut être omise)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Retourner la connexion à la base de données
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL non trouvé", e);
        }
    }
}
