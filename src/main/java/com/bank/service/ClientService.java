package com.bank.service;

import com.bank.database.DatabaseConnection;
import com.bank.model.Client;
import com.bank.model.Banque;  // Si Banque est un objet dans votre modèle

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientService {

    // Méthode pour obtenir un client par ID
    public Client getClientById(String clientId) {
        Client client = null;

        // Assume you have a database connection here
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM clients WHERE client_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, clientId);
                ResultSet resultSet = stmt.executeQuery();

                if (resultSet.next()) {
                    // Récupérer les données du ResultSet et les affecter aux variables
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String adresse = resultSet.getString("adresse");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String banqueString = resultSet.getString("banque"); // Nom de la banque

                    // Convertir clientId en int (assurez-vous que le clientId est bien un nombre)
                    int clientIdInt = Integer.parseInt(clientId);

                    // Créer un nouvel objet Banque si c'est un objet et pas juste un String
                    Banque banque = null;  // Si Banque est une autre entité, initialisez-le ici.
                    if (banqueString != null) {
                        // Initialisez l'objet Banque si nécessaire, par exemple :
                        banque = new Banque(banqueString);
                    }

                    // Créer un nouvel objet Client avec les données récupérées
                    client = new Client(clientIdInt, nom, prenom, adresse, phone, email, String.valueOf(banque));
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception SQL
            e.printStackTrace();
        } catch (Exception e) {
            // Gérer d'autres exceptions
            e.printStackTrace();
        }

        return client;
    }
}
