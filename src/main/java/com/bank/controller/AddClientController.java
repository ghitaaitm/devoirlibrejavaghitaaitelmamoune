package com.bank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import com.bank.model.Client;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddClientController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField phoneField;  // Champ pour le numéro de téléphone
    @FXML
    private TextField emailField;

    @FXML
    private void handleAddClient() {
        // Récupérer les valeurs des champs
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        // Validation des champs
        if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            showError("Tous les champs doivent être remplis.");
            return;
        }

        // Validation du format du numéro de téléphone
        if (!isValidPhoneNumber(phone)) {
            showError("Le numéro de téléphone est invalide.");
            return;
        }

        // Validation du format de l'email
        if (!isValidEmail(email)) {
            showError("L'adresse email est invalide.");
            return;
        }

        // Générer un ID client unique (si nécessaire, sinon utilisez une base de données pour gérer les IDs)
        int clientId = generateClientId();  // Assurez-vous que la logique de génération d'ID est correcte

        // Créer un nouveau client
        Client client = new Client(clientId, "C001", nom, prenom, adresse, phone, email);

        // Sauvegarder le client dans la base de données ou afficher un message de confirmation
        System.out.println("Client ajouté : " + client);

        // Affichage d'une alerte de confirmation
        showConfirmation("Client ajouté avec succès.");

        // Ici vous pouvez ajouter la logique pour sauvegarder ce client dans une base de données
    }

    // Méthode pour valider le numéro de téléphone (10 chiffres)
    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{10}");
    }

    // Méthode pour valider l'email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Méthode pour générer un ID client (vous pouvez aussi utiliser un auto-incrément dans la base de données)
    private int generateClientId() {
        // Exemple d'ID généré. Vous pouvez l'adapter en fonction de vos besoins.
        return (int) (Math.random() * 10000); // Juste pour exemple, utilisez une approche plus robuste.
    }

    // Méthode pour afficher des messages d'erreur
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur d'ajout");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher des messages de confirmation
    private void showConfirmation(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
