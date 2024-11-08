package com.bank.utils;

import com.bank.model.Compte;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompteJsonConverterTest {

    @Test
    public void testCompteToJson() throws JsonProcessingException {
        // Créer un objet Compte pour le test
        Compte compte = new Compte();
        compte.setId(1);
        compte.setNom("Compte de test");
        compte.setSolde(1000.0);

        // Convertir en JSON
        String json = CompteJsonConverter.compteToJson(compte);

        // Vérifier que le JSON contient les champs attendus
        assertNotNull(json, "Le JSON généré ne doit pas être nul.");
        System.out.println("JSON produit: " + json);

        // Vérifier la présence de valeurs spécifiques dans le JSON
        assertTrue(json.contains("\"id\":1"), "Le JSON devrait contenir l'ID du compte.");
        assertTrue(json.contains("\"nom\":\"Compte de test\""), "Le JSON devrait contenir le nom du compte.");
        assertTrue(json.contains("\"solde\":1000.0"), "Le JSON devrait contenir le solde du compte.");
    }

    @Test
    public void testJsonToCompte() throws JsonProcessingException {
        // JSON pour le test
        String json = "{\"id\":1,\"nom\":\"Compte de test\",\"solde\":1000.0}";

        // Convertir le JSON en objet Compte
        Compte compte = CompteJsonConverter.jsonToCompte(json);

        // Vérifier que l'objet Compte est bien construit
        assertNotNull(compte, "L'objet Compte ne doit pas être nul.");
        assertEquals(1, compte.getId(), "L'ID du compte doit être 1.");
        assertEquals("Compte de test", compte.getNom(), "Le nom du compte doit être 'Compte de test'.");
        assertEquals(1000.0, compte.getSolde(), 0.001, "Le solde du compte doit être 1000.0.");
    }
}
