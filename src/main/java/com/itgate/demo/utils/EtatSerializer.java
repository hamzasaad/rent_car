package com.itgate.demo.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.itgate.demo.models.Etat;

import java.io.IOException;
import java.util.List;

public class EtatSerializer extends JsonSerializer<List<Etat>> {
    @Override
    public void serialize(List<Etat> etats, JsonGenerator jgen,
                          SerializerProvider serProv) throws IOException,
            JsonProcessingException {

        jgen.writeStartArray();
        for (Etat model : etats) {
            jgen.writeStartObject();
            jgen.writeObjectField("id", model.getId());
            jgen.writeObjectField("datedebut", model.getDate_depart());
            jgen.writeObjectField("datefin", model.getDate_retour());
            jgen.writeObjectField("heuredebut", model.getHeur_depart());
            jgen.writeObjectField("heurefin", model.getHeur_retour());

            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }
}