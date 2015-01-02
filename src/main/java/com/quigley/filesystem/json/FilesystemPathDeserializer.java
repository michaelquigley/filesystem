package com.quigley.filesystem.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.quigley.filesystem.FilesystemPath;

import java.io.IOException;

public class FilesystemPathDeserializer extends JsonDeserializer<FilesystemPath> {
    @Override
    public FilesystemPath deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if(jp.getCurrentToken() == JsonToken.VALUE_STRING) {
            return new FilesystemPath(jp.getValueAsString());

        } else {
            throw ctxt.mappingException("Expected JSON String");
        }
    }
}