package com.bank.service;

import com.bank.model.Compte;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects; // Importer Objects pour une comparaison sécurisée

public class CompteService {

    private List<Compte> comptes = new ArrayList<>();

    public CompteService() {
        // Initialisation avec quelques comptes pour tester
        comptes.add(new Compte("C123", "Compte Courant", 1000));
        comptes.add(new Compte("C124", "Livret A", 1500));
    }

    // Méthode pour rechercher un compte par numéro
    public Compte findCompteByAccountNumber(String accountNumber) {
        for (Compte compte : comptes) {
            // Utilisation de Objects.equals() pour éviter NullPointerException
            if (Objects.equals(compte.getNumCompte(), accountNumber)) {
                return compte; // Si le compte est trouvé, le retourner
            }
        }
        return null; // Si le compte n'est pas trouvé, retourner null
    }
}
