package com.bank.utils ;
import com.bank.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientJsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Convertir un objet Client en JSON
    public static String clientToJson(Client client) throws JsonProcessingException {
        return objectMapper.writeValueAsString(client);
    }

    // Convertir un JSON en objet Client
    public static Client jsonToClient(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Client.class);
    }

    // Convertir un JSON en Client à partir de JsonNode (si nécessaire)
    public static Client jsonNodeToClient(JsonNode node) throws JsonProcessingException {
        return objectMapper.treeToValue(node, Client.class);
    }
}
