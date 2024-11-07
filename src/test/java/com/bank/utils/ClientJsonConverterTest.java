package com.bank.utils;

import com.bank.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientJsonConverterTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testClientToJson() throws JsonProcessingException {
        // Créer un objet Client pour le test
        Client client = new Client();
        client.setId(1);
        client.setNom("Jean Dupont");
        client.setEmail("jean.dupont@example.com");

        // Convertir en JSON
        String json = ClientJsonConverter.clientToJson(client);

        // Vérifier que le JSON n'est pas nul et contient des données attendues
        assertNotNull(json);
        System.out.println("JSON produit: " + json);
        assertEquals("{\"id\":1,\"nom\":\"Jean Dupont\",\"email\":\"jean.dupont@example.com\"}", json);
    }

    @Test
    public void testJsonToClient() throws JsonProcessingException {
        // JSON pour le test
        String json = "{\"id\":1,\"nom\":\"Jean Dupont\",\"email\":\"jean.dupont@example.com\"}";

        // Convertir le JSON en objet Client
        Client client = ClientJsonConverter.jsonToClient(json);

        // Vérifier que l'objet Client est bien construit
        assertNotNull(client);
        assertEquals(1, client.getId());
        assertEquals("Jean Dupont", client.getNom());
        assertEquals("jean.dupont@example.com", client.getEmail());
    }

    @Test
    public void testJsonNodeToClient() throws JsonProcessingException {
        // JSON pour le test
        String json = "{\"id\":1,\"nom\":\"Jean Dupont\",\"email\":\"jean.dupont@example.com\"}";

        // Créer un JsonNode à partir du JSON
        JsonNode node = objectMapper.readTree(json);

        // Convertir le JsonNode en objet Client
        Client client = ClientJsonConverter.jsonNodeToClient(node);

        // Vérifier que l'objet Client est bien construit
        assertNotNull(client);
        assertEquals(1, client.getId());
        assertEquals("Jean Dupont", client.getNom());
        assertEquals("jean.dupont@example.com", client.getEmail());
    }
}
