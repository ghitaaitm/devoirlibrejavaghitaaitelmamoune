package com.bank.utils;

import com.bank.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.HashMap;
import java.util.Map;

public class TransactionJsonConverter {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Gérer les types java.time si nécessaire
    }

    // Convertir une Transaction en JSON (modifié pour personnaliser la sortie)
    public static String transactionToJson(Transaction transaction) throws JsonProcessingException {
        // Créer un Map pour les champs nécessaires
        Map<String, Object> transactionMap = new HashMap<>();

        // Ajouter les champs nécessaires à la map
        transactionMap.put("id", transaction.getId());
        transactionMap.put("type", transaction.getType().toString());  // Assuming TypeTransaction is an enum
        transactionMap.put("reference", transaction.getReference());
        transactionMap.put("clientId", transaction.getClientId());
        transactionMap.put("date", transaction.getDate());
        transactionMap.put("montant", transaction.getMontant());

        // Convertir la map en JSON
        return objectMapper.writeValueAsString(transactionMap);
    }

    // Convertir un JSON en Transaction
    public static Transaction jsonToTransaction(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Transaction.class);
    }
}
