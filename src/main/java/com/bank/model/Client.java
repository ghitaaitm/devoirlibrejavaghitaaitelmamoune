package com.bank.model;

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

    private int id; // Identifiant unique du client
    private String numClient;  // Numéro de client unique
    private String nom;        // Nom du client
    private String prenom;     // Prénom du client
    private String adresse;    // Adresse du client
    private String phone;      // Numéro de téléphone
    private String email;      // Email du client
    private List<Compte> comptes = new ArrayList<>();  // Liste des comptes associés au client
    private Banque banque;  // Banque associée au client

    // Constructeur complet (avec tous les paramètres)
    public Client(int id, String numClient, String nom, String prenom, String adresse, String phone, String email, Banque banque, List<Compte> comptes) {
        this.id = id;
        this.numClient = numClient;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.phone = phone;
        this.email = email;
        this.banque = banque;
        this.comptes = (comptes != null) ? comptes : new ArrayList<>();
    }

    // Constructeur sans la liste des comptes (initialisation vide de comptes)
    public Client(int id, String numClient, String nom, String prenom, String adresse, String phone, String email, Banque banque) {
        this(id, numClient, nom, prenom, adresse, phone, email, banque, new ArrayList<>());
    }

    // Constructeur pour un client sans banque (par défaut null) et sans liste de comptes (initialisation vide)
    public Client(int id, String numClient, String nom, String prenom, String adresse, String phone, String email) {
        this(id, numClient, nom, prenom, adresse, phone, email, null, new ArrayList<>());
    }

    // Constructeur pour un client avec seulement les informations de base
    public Client(int id, String numClient, String nom, String prenom, String adresse) {
        this(id, numClient, nom, prenom, adresse, "", "", null, new ArrayList<>());
    }

    // Constructeur sans email, mais avec banque et autres informations
    public Client(String numClient, String nom, String prenom, String adresse, String phone, String email, Banque banque) {
        this(0, numClient, nom, prenom, adresse, phone, email, banque, new ArrayList<>());
    }
    // Constructor
    public Client(String nom, String prenom, String adresse, String phone, Banque banque) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.phone = phone;
        this.banque = banque;
    }


    // Méthode pour ajouter un compte à la liste des comptes du client
    public void addCompte(Compte compte) {
        if (this.comptes != null) {
            this.comptes.add(compte);
        }
    }

    public Object getNumCompte() {
        return null;
    }
}
