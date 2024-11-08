package com.bank.utils;

import com.bank.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionJsonConverterTest {

    @Test
    public void testTransactionToJson() throws JsonProcessingException {
        // Create a Transaction instance with the default constructor
        Transaction transaction = new Transaction();

        // Convert to JSON
        String json = TransactionJsonConverter.transactionToJson(transaction);

        // Verify that JSON is not null
        assertNotNull(json);
        System.out.println("Generated JSON: " + json);

        // Expected JSON structure
        String expectedJson = "{\"id\":\"1\",\"type\":\"VIRIN\",\"montant\":200.0,\"date\":\"2024-11-07\",\"reference\":\"TX-123456789\",\"clientId\":12345}";

        // Parse both JSON strings to compare as JSON objects
        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(expectedJson), mapper.readTree(json));
    }

    @Test
    public void testJsonToTransaction() throws JsonProcessingException {
        // JSON for the test
        String json = "{\"id\":\"1\",\"type\":\"VIRIN\",\"montant\":200.0,\"date\":\"2024-11-07\",\"reference\":\"TX-123456789\",\"clientId\":12345}";

        // Convert JSON to Transaction object
        Transaction transaction = TransactionJsonConverter.jsonToTransaction(json);

        // Verify the Transaction object is correctly constructed
        assertNotNull(transaction);
        assertEquals("1", transaction.getId());
        assertEquals(200.0, transaction.getMontant(), 0.001);
        assertEquals("2024-11-07", transaction.getDate());
        assertEquals("TX-123456789", transaction.getReference());
        assertEquals(12345, transaction.getClientId());
    }
}
