package com.bank.database;

import com.bank.model.Compte;
import com.bank.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDAO {

    // Méthode pour insérer un compte dans la base de données
    public void insertCompte(Compte compte) {
        String sql = "INSERT INTO comptes (numCompte, solde, client_id) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Remplir la requête préparée
            statement.setString(1, compte.getNumCompte());
            statement.setDouble(2, compte.getSolde());
            statement.setInt(3, Integer.parseInt(String.valueOf(compte.getClient().getId())));  // Assumer que chaque compte est associé à un client

            // Exécuter la requête
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer un compte à partir de son ID
    public Compte getCompteById(int id) {
        String sql = "SELECT * FROM comptes WHERE id = ?";
        Compte compte = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Remplir la requête préparée avec l'ID
            statement.setInt(1, id);

            // Exécuter la requête et obtenir les résultats
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String numCompte = resultSet.getString("numCompte");
                double solde = resultSet.getDouble("solde");
                int clientId = resultSet.getInt("client_id");

                // Récupérer le client associé à ce compte
                Client client = new ClientDAO().getClientById(clientId);

                compte = new Compte(id, numCompte, solde, client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compte;
    }

    // Méthode pour récupérer tous les comptes
    public List<Compte> getAllComptes() {
        String sql = "SELECT * FROM comptes";
        List<Compte> comptes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Exécuter la requête et obtenir les résultats
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String numCompte = resultSet.getString("numCompte");
                double solde = resultSet.getDouble("solde");
                int clientId = resultSet.getInt("client_id");

                // Récupérer le client associé à ce compte
                Client client = new ClientDAO().getClientById(clientId);

                comptes.add(new Compte(id, numCompte, solde, client));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comptes;
    }

    // Méthode pour mettre à jour un compte dans la base de données
    public void updateCompte(Compte compte) {
        String sql = "UPDATE comptes SET numCompte = ?, solde = ?, client_id = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Remplir la requête préparée
            statement.setString(1, compte.getNumCompte());
            statement.setDouble(2, compte.getSolde());
            statement.setInt(3, Integer.parseInt(String.valueOf(compte.getClient().getId())));
            statement.setInt(4, compte.getId());

            // Exécuter la mise à jour
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un compte par ID
    public void deleteCompte(int id) {
        String sql = "DELETE FROM comptes WHERE id = ?";

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
