package com.bank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddAccountController {

    @FXML
    private TextField numCompteField;

    @FXML
    private DatePicker dateCreaField;

    @FXML
    private TextField deviseField;

    @javafx.fxml.FXML
    private void handleCreateAccount() {
        // Get input values from the UI fields
        String numCompte = numCompteField.getText();
        String devise = deviseField.getText();
        String dateCreation = dateCreaField.getValue().toString(); // Convert date to string

        // Handle account creation logic, e.g., saving to the database
        System.out.println("Account Created: " + numCompte + ", " + devise + ", " + dateCreation);
    }
}
