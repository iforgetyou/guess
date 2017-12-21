package com.zy17.guess.famous.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import weixin.popular.bean.message.EventMessage;

@Converter
@Slf4j
public class EventJpaJsonConverter implements AttributeConverter<EventMessage, String> {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT );
    }
    @Override
    public String convertToDatabaseColumn(EventMessage attribute) {
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage(),ex);
        }
        return jsonString;
    }

    @Override
    public EventMessage convertToEntityAttribute(String dbData) {
        try {
            EventMessage eventMessage = objectMapper.readValue(dbData, EventMessage.class);
            return eventMessage;
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }
        return new EventMessage();
    }

}