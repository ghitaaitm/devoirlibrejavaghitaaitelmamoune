package com.bank.utils;

import com.bank.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientJsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Convert Client object to JSON string
    public static String clientToJson(Client client) throws JsonProcessingException {
        return objectMapper.writeValueAsString(client);
    }

    // Convert JSON string to Client object
    public static Client jsonToClient(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Client.class);
    }

    // Convert JsonNode to Client object
    public static Client jsonNodeToClient(JsonNode node) throws JsonProcessingException {
        return objectMapper.treeToValue(node, Client.class);
    }
}
