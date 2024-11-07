package com.bank.controller;

import com.bank.model.Compte;
import com.bank.model.Transaction;
import com.bank.model.Transaction.TypeTransaction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.UUID;

public class AddTransactionController {

    @FXML
    private ComboBox<String> transactionTypeComboBox;

    @FXML
    private TextField amountField;

    @FXML
    private TextField descriptionField;

    private Compte compte;

    // Set account and populate transaction type choices
    public void setAccount(Compte compte) {
        this.compte = compte;
        transactionTypeComboBox.setItems(FXCollections.observableArrayList("Deposit", "Withdrawal"));
    }

    @FXML
    private void onAddTransaction() {
        String transactionType = transactionTypeComboBox.getValue();
        String amountText = amountField.getText();
        String description = descriptionField.getText();

        // Validate the input fields
        if (transactionType == null || transactionType.isEmpty()) {
            showAlert("Please select a transaction type.");
            return;
        }
        if (amountText == null || amountText.isEmpty()) {
            showAlert("Please enter an amount.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid numeric amount.");
            return;
        }

        if (amount <= 0) {
            showAlert("Amount must be a positive value.");
            return;
        }

        // Create the transaction object
        Transaction transaction;
        try {
            transaction = new Transaction(
                    generateUniqueTransactionId(),
                    TypeTransaction.valueOf(transactionType.toUpperCase()), // Convert transaction type to match enum constants
                    amount,
                    description,
                    compte
            );
        } catch (IllegalArgumentException e) {
            showAlert("Invalid transaction type selected.");
            return;
        }

        // Update the account balance based on transaction type
        if (transactionType.equalsIgnoreCase("Deposit")) {
            compte.setSolde(compte.getSolde() + amount);
        } else if (transactionType.equalsIgnoreCase("Withdrawal")) {
            if (compte.getSolde() < amount) {
                showAlert("Insufficient balance for withdrawal.");
                return;
            }
            compte.setSolde(compte.getSolde() - amount);
        }

        // Close the window after adding the transaction
        Stage stage = (Stage) amountField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) amountField.getScene().getWindow();
        stage.close();
    }

    // Utility method to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Improved unique ID generator using UUID
    private String generateUniqueTransactionId() {
        return UUID.randomUUID().toString();  // Unique ID based on UUID
    }
}
