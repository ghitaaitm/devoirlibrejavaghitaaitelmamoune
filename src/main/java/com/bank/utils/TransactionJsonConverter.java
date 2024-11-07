package com.bank.utils;

import com.bank.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionJsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Convertir un objet Transaction en JSON
    public static String transactionToJson(Transaction transaction) throws JsonProcessingException {
        return objectMapper.writeValueAsString(transaction);
    }

    // Convertir un JSON en objet Transaction
    public static Transaction jsonToTransaction(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Transaction.class);
    }
}
