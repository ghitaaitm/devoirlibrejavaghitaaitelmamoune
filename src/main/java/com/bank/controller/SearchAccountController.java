package com.bank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import com.bank.model.Compte;
import com.bank.service.CompteService; // Assurez-vous d'avoir un service pour gérer les comptes

public class SearchAccountController {

    @FXML
    private TextField accountNumberField; // Champ pour entrer le numéro de compte à rechercher
    @FXML
    private Label accountDetailsLabel; // Label pour afficher les informations du compte

    private CompteService compteService = new CompteService(); // Service pour récupérer le compte

    @FXML
    private void handleSearchAccount() {
        String accountNumber = accountNumberField.getText().trim();

        // Validation pour vérifier que le champ n'est pas vide
        if (accountNumber.isEmpty()) {
            showError("Le numéro de compte est requis.");
            return;
        }

        // Rechercher le compte dans la base de données
        Compte compte = compteService.findCompteByAccountNumber(accountNumber);

        if (compte != null) {
            displayAccountDetails(compte);
        } else {
            showError("Compte non trouvé.");
        }
    }

    private void displayAccountDetails(Compte compte) {
        // Affichage des détails du compte dans le label
        String accountDetails = "Numéro de compte: " + compte.getNumCompte() + "\n"
                + "Type de compte: " + compte.getTypeCompte() + "\n"
                + "Solde: " + compte.getSolde();
        accountDetailsLabel.setText(accountDetails);
    }

    // Méthode pour afficher des messages d'erreur
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur de recherche");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
