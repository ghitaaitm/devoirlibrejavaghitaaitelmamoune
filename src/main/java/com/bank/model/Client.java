package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client {

    @JsonProperty("id")
    private int id; // Identifiant unique du client

    @JsonProperty("numClient")
    private String numClient;  // Numéro de client unique

    @JsonProperty("nom")
    private String nom;        // Nom du client

    @JsonProperty("prenom")
    private String prenom;     // Prénom du client

    @JsonProperty("adresse")
    private String adresse;    // Adresse du client

    @JsonProperty("phone")
    private String phone;      // Numéro de téléphone

    @JsonProperty("email")
    private String email;      // Email du client

    @JsonProperty("comptes")
    private List<Compte> comptes = new ArrayList<>();  // Liste des comptes associés au client

    @JsonIgnore
    private Banque banque;  // Banque associée au client

    @JsonIgnore
    private String numCompte; // Ce champ peut être ignoré s'il n'est pas nécessaire

    // Constructeur pour un client avec seulement les informations essentielles
    public Client(int id, String numClient, String nom, String prenom, String adresse, String phone, String email) {
        this.id = id;
        this.numClient = numClient;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.phone = phone;
        this.email = email;
        this.comptes = new ArrayList<>(); // Initialiser la liste des comptes vide
    }

    // Méthode pour ajouter un compte à la liste des comptes du client
    public void addCompte(Compte compte) {
        if (this.comptes != null) {
            this.comptes.add(compte);
        }
    }

    // Méthode pour obtenir un numéro de compte (si nécessaire)
    public Object getNumCompte() {
        return this.numCompte;  // Retourne le numCompte
    }
}
