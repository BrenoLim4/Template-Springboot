package br.gov.ce.sop.convenios.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class StringEncrypted extends StdDeserializer<String> {
    @Value("${password.key}")
    private String secretKey;
    private static final long serialVersionUID = 7527542687158493910L;


    public StringEncrypted() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return CryptoUtils.decrypt(_parseString(p, ctxt, NullsConstantProvider.nuller()).getBytes(), secretKey.getBytes());
    }

}