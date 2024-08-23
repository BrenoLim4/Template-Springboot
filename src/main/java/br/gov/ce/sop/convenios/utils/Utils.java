package br.gov.ce.sop.convenios.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, Object> mapObjectToHashMap(Object object) {
        Map<String, Object> resultMap = new HashMap<>();
        Arrays.stream(object.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        Object value = field.get(object);
                        resultMap.put(field.getName(), value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        return resultMap;
    }

    public static File convertBase64ToFile(String base64, String fileName){
        // Decode Base64 string to bytes
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        File file = new File(fileName);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(decodedBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
