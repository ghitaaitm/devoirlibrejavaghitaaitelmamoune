package com.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banque {
    private int id;
    private String pays;
    private List<Compte> comptes;  // Liste des comptes associés à cette banque

    // Constructeur standard avec id et pays
    public Banque(int id, String pays) {
        this.id = id;
        this.pays = pays;
        this.comptes = new ArrayList<>();  // Initialiser la liste des comptes vide par défaut
    }

    // Constructeur qui crée une banque à partir d'un nom de banque (par exemple)
    public Banque(String banqueString) {
        this.pays = banqueString;  // Assumer que le String représente le pays ou le nom de la banque
        this.comptes = new ArrayList<>();  // Initialiser la liste des comptes vide par défaut
    }

    // Ajouter un compte à la banque
    public void addCompte(Compte compte) {
        this.comptes.add(compte);
    }

    // Retirer un compte de la banque
    public void removeCompte(Compte compte) {
        this.comptes.remove(compte);
    }
}
