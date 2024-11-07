package com.bank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import com.bank.model.Compte;

import java.time.LocalDate;

public class CreateAccountController {

    @FXML
    private TextField numCompteField;
    @FXML
    private DatePicker dateCreaField;
    @FXML
    private TextField deviseField;

    @FXML
    private void handleCreateAccount() {
        int numCompte = Integer.parseInt(numCompteField.getText());
        LocalDate localDate = dateCreaField.getValue();
        String devise = deviseField.getText();

        Compte compte = new Compte(String.valueOf(numCompteField), devise);
        // Sauvegardez le compte dans la base de données
        System.out.println("Compte créé : " + compte);
    }
}
