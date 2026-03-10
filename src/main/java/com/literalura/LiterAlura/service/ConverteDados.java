package com.literalura.LiterAlura.service;

import tools.jackson.databind.json.JsonMapper;
import tools.jackson.core.JacksonException;

public class ConverteDados implements IConverteDados {
    private JsonMapper mapper = JsonMapper.builder().build();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JacksonException e) {
            throw new RuntimeException("Erro ao converter JSON no Spring 4: " + e.getMessage());
        }
    }
}