package io.bluebeaker.legacyponder.utils;

import net.minecraft.client.resources.I18n;

public class TextUtils {
    public static String formatKey(String key){
        return I18n.format(key);
    }
    public static String[] formatLines(String text){
        return splitLines(I18n.format(text));
    }

    public static String[] splitLines(String text1) {
        return text1.replace("\\n", "\n").split("\n");
    }
}
