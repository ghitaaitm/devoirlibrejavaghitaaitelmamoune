package com.bank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SearchClientController {

    @FXML
    private TextField clientIdField;

    @FXML
    private TextArea resultArea;

    @FXML
    public void handleSearchClient() {
        String clientIdStr = clientIdField.getText();

        // Check if the clientId field is empty
        if (clientIdStr.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un ID client pour la recherche !");
            return;
        }

        // Validate if the input is a valid integer
        if (!isValidInteger(clientIdStr)) {
            showAlert("Erreur", "L'ID client doit être un nombre valide !");
            return;
        }

        // Parse the clientId as an integer after validation
        int clientId = Integer.parseInt(clientIdStr);

        // Logique pour rechercher le client dans la base de données ou autre source de données
        String clientInfo = searchClientInDatabase(clientId);

        if (clientInfo != null) {
            resultArea.setText(clientInfo); // Afficher les informations du client dans la zone de texte
        } else {
            resultArea.setText("Client non trouvé.");
        }
    }

    private String searchClientInDatabase(int clientId) {
        // Simuler la recherche dans la base de données
        if (clientId == 123) {
            return "Nom: Dupont\nPrénom: Jean\nAdresse: 123 Rue Exemple\nTéléphone: 0123456789";
        } else {
            return null; // Simuler que le client n'a pas été trouvé
        }
    }

    // Méthode utilitaire pour afficher des alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to check if the input is a valid integer
    private boolean isValidInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false; // empty string is not a valid integer
        }
        // Check if each character is a digit
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false; // non-digit character found
            }
        }
        return true; // valid integer string
    }
}
