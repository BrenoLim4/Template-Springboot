package br.gov.ce.sop.convenios.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class StringDeserializer extends StdDeserializer<String> {

    private static final long serialVersionUID = 7527542687158493910L;

    public StringDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return _parseString(p, ctxt, NullsConstantProvider.nuller()).trim().toLowerCase();
    }

}