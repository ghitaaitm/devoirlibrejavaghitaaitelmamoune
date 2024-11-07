package com.bank.controller;
import com.bank.model.Compte;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewAccountController {

    @FXML
    private TextField accountIdField;

    @FXML
    private TextField accountHolderNameField;

    @FXML
    private TextField balanceField;

    private Compte account;

    public void setAccount(Compte account) {
        this.account = account;
        loadAccountData();
    }

    private void loadAccountData() {
        accountIdField.setText(String.valueOf(account.getId()));
        accountHolderNameField.setText(account.getNumCompte());
        balanceField.setText(String.valueOf(account.getSolde()));
    }

    @FXML
    private void onEditAccount() {
        // Logique pour ouvrir l'interface de modification du compte
        EditAccountController editController = new EditAccountController();
        editController.setAccount(account);

        // Ouvrez une nouvelle fenêtre pour éditer le compte
        Stage stage = new Stage();
        // Ajouter le code pour ouvrir l'interface de modification
    }
}
