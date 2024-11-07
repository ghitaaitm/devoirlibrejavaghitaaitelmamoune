package com.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@Builder
public class Compte {
    private String numCompte;
    private Date dateCreation;  // Date de création du compte
    private Date dateUpdate;    // Date de dernière mise à jour
    private String devise;      // Devise du compte (EUR, USD, etc.)
    private double solde;       // Solde du compte
    private int id;             // Identifiant unique du compte
    private Banque banque;      // Banque à laquelle appartient le compte
    private Client client;      // Client associé au compte
    private List<Transaction> transactions;  // Liste des transactions effectuées sur le compte
    private String nom;
    private String typeCompte;//on peut la négliger

    // Constructeur par défaut
    public Compte(String c123, String compteCourant, int i) {
        this.numCompte = null;
        this.solde = 0.0;
        this.banque = null;
        this.client = null;  // Pas de client associé par défaut
        this.transactions = new ArrayList<>();  // Initialiser la liste des transactions
        this.dateCreation = new Date();
        this.dateUpdate = new Date();
        this.devise = "EUR";  // Devise par défaut
        this.nom = null;  // Pas de nom par défaut
    }

    // Constructeur avec paramètres
    public Compte() {
        this.id = id;
        this.numCompte = numCompte;
        this.solde = solde;
        this.banque = banque;
        this.client = client;  // Associer le compte à un client
        this.dateCreation = new Date();
        this.dateUpdate = new Date();
        this.devise = "EUR";  // Devise par défaut
        this.transactions = new ArrayList<>();  // Initialiser la liste des transactions
        this.nom = null;  // Pas de nom par défaut
    }

    // Constructeur avec paramètres uniquement pour le client
    public Compte(int id, String numCompte, double solde, Client client) {
        this();  // Associer le compte à un client sans banque
    }

    // Constructeur avec paramètres pour les informations supplémentaires
    public Compte(String numCompte, Date dateCreation, String devise, Banque banque, Client client) {
        this.numCompte = numCompte;  // Utiliser numCompte tel quel (assurez-vous qu'il est un String)
        this.dateCreation = dateCreation;  // Directement utiliser Date
        this.dateUpdate = new Date();  // Date actuelle comme date de mise à jour
        this.devise = devise;  // Devise
        this.solde = 0.0;  // Solde par défaut
        this.banque = banque;  // Banque associée
        this.client = client;  // Client associé
        this.transactions = new ArrayList<>();  // Initialiser la liste des transactions
        this.nom = null;  // Pas de nom par défaut
    }

    // Constructeur pour un compte avec un ID et un nom (pour une utilisation spécifique)
    public Compte(int compteId) {
        this.id = compteId;
        this.nom = nom;  // Associer un nom
        this.numCompte = null;
        this.solde = 0.0;
        this.banque = null;
        this.client = null;
        this.transactions = new ArrayList<>();  // Initialiser la liste des transactions
        this.dateCreation = new Date();
        this.dateUpdate = new Date();
        this.devise = "EUR";  // Devise par défaut
    }
    // Example constructor that takes a String for numCompte and devise
    public Compte(String numCompte, String devise) {
        this.numCompte = numCompte;
        this.devise = devise;
        this.solde = 0.0;
        this.dateCreation = new Date();
        this.dateUpdate = new Date();
        this.transactions = new ArrayList<>();
        this.banque = null;
        this.client = null;
    }

    // Ajouter une transaction au compte
    public void addTransaction(Transaction transaction) {
        if (this.transactions != null) {
            this.transactions.add(transaction);
        }
    }

    // Retirer une transaction du compte
    public void removeTransaction(Transaction transaction) {
        if (this.transactions != null) {
            this.transactions.remove(transaction);
        }
    }

    public String getTypeCompte() {
        return typeCompte;
    }
}
