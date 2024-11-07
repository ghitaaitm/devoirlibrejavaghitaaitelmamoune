package com.bank.utils;

import com.bank.model.Compte;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        // Vérifier que le JSON n'est pas nul et contient des données attendues
        assertNotNull(json);
        System.out.println("JSON produit: " + json);
        assertEquals("{\"id\":1,\"nom\":\"Compte de test\",\"solde\":1000.0}", json);
    }

    @Test
    public void testJsonToCompte() throws JsonProcessingException {
        // JSON pour le test
        String json = "{\"id\":1,\"nom\":\"Compte de test\",\"solde\":1000.0}";

        // Convertir le JSON en objet Compte
        Compte compte = CompteJsonConverter.jsonToCompte(json);

        // Vérifier que l'objet Compte est bien construit
        assertNotNull(compte);
        assertEquals(1, compte.getId());
        assertEquals("Compte de test", compte.getNom());
        assertEquals(1000.0, compte.getSolde(), 0.001);
    }
}
