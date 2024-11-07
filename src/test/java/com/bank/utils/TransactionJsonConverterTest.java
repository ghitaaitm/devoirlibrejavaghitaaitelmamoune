package com.bank.utils;

import com.bank.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionJsonConverterTest {

    @Test
    public void testTransactionToJson() throws JsonProcessingException {
        // Créer un objet Transaction pour le test
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setMontant(200.0);
        transaction.setDate("2024-11-07");
        transaction.setDescription("Transaction de test");

        // Convertir en JSON
        String json = TransactionJsonConverter.transactionToJson(transaction);

        // Vérifier que le JSON n'est pas nul et contient des données attendues
        assertNotNull(json);
        System.out.println("JSON produit: " + json);
        assertEquals("{\"id\":1,\"montant\":200.0,\"date\":\"2024-11-07\",\"description\":\"Transaction de test\"}", json);
    }

    @Test
    public void testJsonToTransaction() throws JsonProcessingException {
        // JSON pour le test
        String json = "{\"id\":1,\"montant\":200.0,\"date\":\"2024-11-07\",\"description\":\"Transaction de test\"}";

        // Convertir le JSON en objet Transaction
        Transaction transaction = TransactionJsonConverter.jsonToTransaction(json);

        // Vérifier que l'objet Transaction est bien construit
        assertNotNull(transaction);
        assertEquals(1, transaction.getId());
        assertEquals(200.0, transaction.getMontant(), 0.001);
        assertEquals("2024-11-07", transaction.getDate());
        assertEquals("Transaction de test", transaction.getDescription());
    }
}
