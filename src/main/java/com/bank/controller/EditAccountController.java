package com.bank.controller;

import com.bank.model.Compte;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditAccountController {

    @FXML
    private TextField accountIdField;

    @FXML
    private TextField accountHolderNameField;

    @FXML
    private TextField balanceField;

    private Compte account;

    // Method to set the account and load its data into the fields
    public void setAccount(Compte account) {
        this.account = account;
        loadAccountData();
    }

    // Loads account data into the input fields
    private void loadAccountData() {
        accountIdField.setText(String.valueOf(account.getId()));
        accountHolderNameField.setText(account.getNumCompte());
        balanceField.setText(String.valueOf(account.getSolde()));
    }

    @FXML
    private void onSaveChanges() {
        // Validate input fields
        String accountHolderName = accountHolderNameField.getText();
        String balanceText = balanceField.getText();

        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            showAlert("Account holder name cannot be empty.");
            return;
        }

        double balance;
        try {
            balance = Double.parseDouble(balanceText);
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid numeric balance.");
            return;
        }

        // Update the account object
        account.setNumCompte(accountHolderName);
        account.setSolde(balance);

        // TODO: Add code to save the updated account information to the database

        // Close the window after saving changes
        closeWindow();
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    // Utility method to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Utility method to close the current window
    private void closeWindow() {
        Stage stage = (Stage) accountHolderNameField.getScene().getWindow();
        stage.close();
    }
}
