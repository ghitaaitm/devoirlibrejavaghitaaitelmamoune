package com.bank.controller;

import com.bank.model.Transaction;
import com.bank.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchTransactionController {

    @FXML
    private TextField transactionIdField;
    @FXML
    private TextField clientIdField;
    @FXML
    private ListView<String> transactionListView;  // Adjusted type to String for displaying details

    @FXML
    public void handleSearchTransaction(ActionEvent event) {
        String transactionIdStr = transactionIdField.getText();
        String clientIdStr = clientIdField.getText();

        if (transactionIdStr.isEmpty() && clientIdStr.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un ID de transaction ou de client pour la recherche !");
            return;
        }

        try {
            int transactionId = !transactionIdStr.isEmpty() ? Integer.parseInt(transactionIdStr) : -1;
            int clientId = !clientIdStr.isEmpty() ? Integer.parseInt(clientIdStr) : -1;

            // Rechercher les transactions en fonction des critères
            searchTransactions(transactionId, clientId);

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer un ID valide.");
        }
    }

    private void searchTransactions(int transactionId, int clientId) {
        String query = "SELECT * FROM transactions WHERE ";
        if (transactionId > 0 && clientId > 0) {
            query += "id = ? OR client_id = ?";
        } else if (transactionId > 0) {
            query += "id = ?";
        } else if (clientId > 0) {
            query += "client_id = ?";
        } else {
            showAlert("Erreur", "Veuillez entrer un ID de transaction ou de client pour la recherche !");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            if (transactionId > 0 && clientId > 0) {
                stmt.setInt(1, transactionId);
                stmt.setInt(2, clientId);
            } else if (transactionId > 0) {
                stmt.setInt(1, transactionId);
            } else {
                stmt.setInt(1, clientId);
            }

            ResultSet resultSet = stmt.executeQuery();
            transactionListView.getItems().clear();  // Clear previous results

            while (resultSet.next()) {
                String transactionDetails = "ID: " + resultSet.getInt("id") + " | Montant: " + resultSet.getDouble("montant");
                transactionListView.getItems().add(transactionDetails);  // Add transaction info to the list
            }

            if (transactionListView.getItems().isEmpty()) {
                showAlert("Erreur", "Aucune transaction trouvée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception for debugging
            showAlert("Erreur", "Une erreur s'est produite lors de la recherche de la transaction.\n" + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        if (title.equals("Erreur")) {
            alert.setAlertType(AlertType.ERROR);  // Use error alert for error messages
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
