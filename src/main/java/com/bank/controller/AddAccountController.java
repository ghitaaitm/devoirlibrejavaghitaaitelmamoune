package com.bank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddAccountController {

    @FXML
    private TextField numCompteField;

    @FXML
    private DatePicker dateCreaField;

    @FXML
    private TextField deviseField;

    @FXML
    private void handleCreateAccount() {
        // Get input values from the UI fields
        String numCompte = numCompteField.getText();
        String devise = deviseField.getText();
        String dateCreation = null;

        // Check if the DatePicker has a selected value
        if (dateCreaField.getValue() != null) {
            dateCreation = dateCreaField.getValue().toString(); // Convert date to string
        } else {
            // Show an alert if no date is selected
            showAlert("Erreur", "Veuillez sélectionner une date de création.");
            return; // Exit the method if date is not selected
        }

        // Handle account creation logic, e.g., saving to the database
        System.out.println("Account Created: " + numCompte + ", " + devise + ", " + dateCreation);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
