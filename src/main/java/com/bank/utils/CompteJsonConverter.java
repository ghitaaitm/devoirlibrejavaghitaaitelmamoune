package com.bank.utils;

import com.bank.model.Compte;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CompteJsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Convertir un objet Compte en JSON
    public static String compteToJson(Compte compte) throws JsonProcessingException {
        return objectMapper.writeValueAsString(compte);
    }

    // Convertir un JSON en objet Compte
    public static Compte jsonToCompte(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Compte.class);
    }
}
