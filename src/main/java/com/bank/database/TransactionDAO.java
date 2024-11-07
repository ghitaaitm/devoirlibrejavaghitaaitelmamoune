package com.bank.database;

import com.bank.model.Transaction;
import com.bank.model.Compte;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

class TransactionDAO {

    private static final String INSERT_TRANSACTION_SQL = "INSERT INTO transactions (type, timestamp, reference) VALUES (?, ?, ?)";
    private static final String SELECT_TRANSACTION_BY_ID_SQL = "SELECT * FROM transactions WHERE id = ?";
    private static final String SELECT_COMPTES_FOR_TRANSACTION_SQL =
            "SELECT c.id, c.nom FROM comptes c JOIN transaction_comptes tc ON c.id = tc.compte_id WHERE tc.transaction_id = ? AND tc.type = ?";
    private static final String INSERT_TRANSACTION_COMPTES_SQL =
            "INSERT INTO transaction_comptes (transaction_id, compte_id, type) VALUES (?, ?, ?)";
    private static final String DELETE_TRANSACTION_COMPTES_SQL =
            "DELETE FROM transaction_comptes WHERE transaction_id = ?";
    private static final String UPDATE_TRANSACTION_SQL =
            "UPDATE transactions SET type = ?, timestamp = ?, reference = ? WHERE id = ?";
    private static final String DELETE_TRANSACTION_SQL =
            "DELETE FROM transactions WHERE id = ?";

    // Méthode pour insérer une transaction dans la base de données
    public void insertTransaction(Transaction transaction) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_TRANSACTION_SQL, Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false); // Start transaction

            // Remplir les valeurs de la requête préparée
            statement.setString(1, transaction.getType().name());
            statement.setTimestamp(2, java.sql.Timestamp.from(transaction.getTimeStamp()));  // Conversion Instant to Timestamp
            statement.setString(3, transaction.getReference());

            // Exécuter la requête
            statement.executeUpdate();

            // Récupérer l'ID de la transaction insérée (si nécessaire pour les relations)
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int transactionId = generatedKeys.getInt(1);
                updateTransactionComptes(connection, transactionId, transaction);
            }

            connection.commit(); // Commit transaction

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, e.g., rollback the transaction if needed
        }
    }

    // Méthode pour récupérer une transaction à partir de son ID
    public Transaction getTransactionById(int id) {
        Transaction transaction = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TRANSACTION_BY_ID_SQL)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String type = resultSet.getString("type");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                String reference = resultSet.getString("reference");

                // Construct Transaction object
                Set<Compte> comptesSource = getComptesForTransaction(connection, id, true);  // Get source comptes
                Set<Compte> comptesDest = getComptesForTransaction(connection, id, false);  // Get destination comptes

                transaction = new Transaction(type, reference, comptesSource, comptesDest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    // Méthode pour récupérer les comptes associés à une transaction
    private Set<Compte> getComptesForTransaction(Connection connection, int transactionId, boolean isSource) {
        Set<Compte> comptes = new HashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_COMPTES_FOR_TRANSACTION_SQL)) {
            statement.setInt(1, transactionId);
            statement.setString(2, isSource ? "source" : "destination");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int compteId = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                comptes.add(new Compte(compteId)); // Assuming a constructor in Compte with id and name
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comptes;
    }

    // Méthode pour mettre à jour une transaction dans la base de données
    public void updateTransaction(Transaction transaction) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TRANSACTION_SQL)) {

            statement.setString(1, transaction.getType().name());
            statement.setTimestamp(2, Timestamp.from(transaction.getTimeStamp()));  // Conversion Instant to Timestamp
            statement.setString(3, transaction.getReference());
            statement.setInt(4, Integer.parseInt(transaction.getId()));

            statement.executeUpdate();

            // Optionally update the associated comptes in the database if needed
            updateTransactionComptes(connection, Integer.parseInt(transaction.getId()), transaction);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer une transaction par ID
    public void deleteTransaction(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TRANSACTION_SQL)) {

            connection.setAutoCommit(false); // Start transaction

            statement.setInt(1, id);
            statement.executeUpdate();

            // Optionally, delete the associated comptes records in the database if necessary
            deleteTransactionComptes(connection, id);

            connection.commit(); // Commit transaction

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, e.g., rollback the transaction if needed
        }
    }

    // Méthode pour gérer les relations avec les comptes associés à la transaction
    private void updateTransactionComptes(Connection connection, int transactionId, Transaction transaction) {
        try (PreparedStatement statementSource = connection.prepareStatement(INSERT_TRANSACTION_COMPTES_SQL);
             PreparedStatement statementDest = connection.prepareStatement(INSERT_TRANSACTION_COMPTES_SQL)) {

            // Insert source comptes
            for (Compte compte : transaction.getComptesSource()) {
                statementSource.setInt(1, transactionId);
                statementSource.setInt(2, compte.getId());
                statementSource.setString(3, "source");
                statementSource.addBatch();
            }
            statementSource.executeBatch();

            // Insert destination comptes
            for (Compte compte : transaction.getComptesDest()) {
                statementDest.setInt(1, transactionId);
                statementDest.setInt(2, compte.getId());
                statementDest.setString(3, "destination");
                statementDest.addBatch();
            }
            statementDest.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer les comptes associés à une transaction
    private void deleteTransactionComptes(Connection connection, int transactionId) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_TRANSACTION_COMPTES_SQL)) {

            statement.setInt(1, transactionId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
