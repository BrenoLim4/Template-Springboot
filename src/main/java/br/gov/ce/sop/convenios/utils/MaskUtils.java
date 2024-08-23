package br.gov.ce.sop.convenios.utils;

import jakarta.annotation.Nullable;

public class MaskUtils {
    public static String genericMask(@Nullable String content, String mask, Character character, @Nullable Character hiddenCharacter) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        if (mask.isEmpty()) return content;
        StringBuilder result = new StringBuilder();
        int i=0;
        for (int j = 0; j< mask.length(); j++) {
            if (mask.charAt(j) == character) {
                result.append(content.toCharArray().length > i ? content.charAt(i) : "");
                i++;
            } else if (hiddenCharacter != null && mask.charAt(j) == hiddenCharacter) {
                result.append(hiddenCharacter);
                i++;
            } else {
                result.append(mask.charAt(j));
            }
        }
        return result.toString();
    }

    public static String cpnjMask(@Nullable String content, Character c){
        String mask = "__.___.___/____-__".replaceAll("_", c.toString());
        return genericMask(content, mask, c, null);
    }

    public static String cpfMask(@Nullable String content, Character c){
        String mask;
        char hiddenCaracter;
        if (c != '*') {
            mask = "___.***.***-__".replaceAll("_", c.toString());
            hiddenCaracter = '*';
        } else {
            mask = "___.###.###-__".replaceAll("_", c.toString());
            hiddenCaracter = '#';
        }

        return genericMask(content, mask, c, hiddenCaracter);
    }

    public static String viprocMask(@Nullable String content, Character c){
        String mask = "________/____".replaceAll("_", c.toString());
        return genericMask(content, mask, c, null);
    }

    public static String suiteMask(@Nullable String content, Character c){
        String mask = "_____.______/____-__".replaceAll("_", c.toString());
        return genericMask(content, mask, c, null);
    }
}
