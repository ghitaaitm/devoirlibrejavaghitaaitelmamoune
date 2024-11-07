package com.bank.database;

import com.bank.model.Client;
import java.sql.*;

public class ClientDAO {

    // Méthode pour insérer un client dans la base de données
    public void insertClient(Client client) {
        String sql = "INSERT INTO clients (numClient, nom, prenom, adresse) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Remplir la requête préparée
            statement.setString(1, client.getNumClient());
            statement.setString(2, client.getNom());
            statement.setString(3, client.getPrenom());
            statement.setString(4, client.getAdresse());

            // Exécuter la requête
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer un client à partir de son ID
    public Client getClientById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        Client client = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Remplir la requête préparée avec l'ID
            statement.setInt(1, id);

            // Exécuter la requête et obtenir les résultats
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String numClient = resultSet.getString("numClient");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adresse = resultSet.getString("adresse");

                client = new Client();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

    // Méthode pour mettre à jour un client dans la base de données
    public void updateClient(Client client) {
        String sql = "UPDATE clients SET numClient = ?, nom = ?, prenom = ?, adresse = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Remplir la requête préparée
            statement.setString(1, client.getNumClient());
            statement.setString(2, client.getNom());
            statement.setString(3, client.getPrenom());
            statement.setString(4, client.getAdresse());
            statement.setInt(5, Integer.parseInt(String.valueOf(client.getId())));

            // Exécuter la mise à jour
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un client par ID
    public void deleteClient(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Remplir la requête préparée avec l'ID
            statement.setInt(1, id);

            // Exécuter la suppression
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
