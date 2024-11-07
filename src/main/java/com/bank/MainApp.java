package com.bank;

import com.bank.model.Banque;
import com.bank.model.Client;
import com.bank.model.Transaction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    @FXML
    private TextField clientIdField;
    @FXML
    private TextField banqueIdField;
    @FXML
    private TextField banquePaysField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField transactionIdField;
    @FXML
    private TextField numCompteField;
    @FXML
    private ListView<String> transactionListView;

    private List<Banque> banques = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        try {
            openWindow("/fxml/add_client.fxml", "Ajouter un Client");
            openWindow("/fxml/create_account.fxml", "Création de Compte");
            openWindow("/fxml/search_client.fxml", "Recherche de Client");
            openWindow("/fxml/search_transaction.fxml", "Recherche de Transaction");
            openWindow("/fxml/search_account.fxml", "Recherche de Compte");

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/add_client.fxml"));
            primaryStage.setTitle("Gestion de Clients");
            primaryStage.setScene(new Scene(root, 400, 300));
            primaryStage.show();
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'ouverture de la fenêtre principale : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 400, 300));
            stage.show();
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'ouverture de la fenêtre : " + title + "\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearchClient(ActionEvent event) {
        String clientIdStr = clientIdField.getText();
        String numCompte = numCompteField.getText();

        // Check if both fields are empty
        if (clientIdStr.isEmpty() && numCompte.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un ID client ou un NumCompte pour la recherche !");
            return;
        }

        // Validate the format of the NumCompte if it's provided
        if (!numCompte.isEmpty() && !isValidNumCompte(numCompte)) {
            showAlert("Erreur", "Le format de NumCompte est invalide !");
            return;
        }

        // Validate and parse client ID
        int clientId = -1;
        if (!clientIdStr.isEmpty()) {
            if (isValidInteger(clientIdStr)) {
                clientId = Integer.parseInt(clientIdStr);
                if (clientId <= 0) {
                    showAlert("Erreur", "L'ID client doit être un nombre positif !");
                    return;
                }
            } else {
                showAlert("Erreur", "L'ID client doit être un nombre valide !");
                return;
            }
        }

        // Proceed with the search operation
        searchClientInList(clientId, numCompte);
    }

    // Helper method to check if the string is a valid integer
    private boolean isValidInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // Check if the string contains only digits (no letters or special characters)
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void searchClientInList(int clientId, String numCompte) {
        boolean found = false;

        for (Client client : clients) {
            if ((clientId != -1 && client.getId() == clientId) ||
                    (!numCompte.isEmpty() && client.getNumCompte().equals(numCompte))) {
                showAlert("Succès", "Client trouvé : " + client.getNom() + " " + client.getPrenom());
                found = true;
                break;
            }
        }

        if (!found) {
            showAlert("Erreur", "Client non trouvé.");
        }
    }

    @FXML
    public void handleSearchTransaction(ActionEvent event) {
        String transactionIdStr = transactionIdField.getText();

        if (transactionIdStr.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un ID de transaction pour la recherche !");
            return;
        }

        try {
            int transactionId = Integer.parseInt(transactionIdStr);
            if (transactionId <= 0) {
                showAlert("Erreur", "L'ID de transaction doit être un nombre positif !");
                return;
            }
            searchTransactionInList(transactionId);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Saisie erronée ! L'ID de transaction doit être un nombre valide.");
        }
    }

    private void searchTransactionInList(int transactionId) {
        boolean found = false;

        for (Transaction transaction : transactions) {
            try {
                // Ensure the transaction ID is properly handled
                String transactionIdStr = transaction.getId(); // Assuming getId() returns a String
                if (transactionIdStr != null && transactionIdStr.matches("\\d+")) { // Check if it's a valid number string
                    int transactionIdFromList = Integer.parseInt(transactionIdStr);
                    if (transactionIdFromList == transactionId) {
                        showAlert("Succès", "Transaction trouvée : " + transaction.getDetails());
                        found = true;
                        break;
                    }
                } else {
                    // Handle invalid transaction ID format in the list
                    showAlert("Erreur", "ID de transaction invalide dans les données.");
                    return;
                }
            } catch (NumberFormatException e) {
                // Handle any unexpected parsing issues
                showAlert("Erreur", "ID de transaction invalide dans les données.");
                return;
            }
        }

        if (!found) {
            showAlert("Erreur", "Transaction non trouvée.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(title.equals("Erreur") ? AlertType.ERROR : AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidNumCompte(String numCompte) {
        // Validation simple que le numéro de compte contient uniquement des chiffres et est d'au moins 10 caractères
        return numCompte != null && numCompte.matches("\\d{10,}"); // 10 chiffres ou plus
    }

    public static void main(String[] args) {
        launch(args);
    }
}
